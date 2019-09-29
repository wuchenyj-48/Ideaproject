
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.DateUtils;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.core.consts.SerialRuleConsts;
import fortec.mscm.order.consts.DictConsts;
import fortec.mscm.order.entity.Delivery;
import fortec.mscm.order.entity.DeliveryItem;
import fortec.mscm.order.entity.DeliveryItemSn;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.mapper.DeliveryItemSnMapper;
import fortec.mscm.order.mapper.DeliveryMapper;
import fortec.mscm.order.request.DeliveryQueryRequest;
import fortec.mscm.order.service.DeliveryItemService;
import fortec.mscm.order.service.DeliveryService;
import fortec.mscm.order.service.PurchaseOrderItemService;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 发货单 service 实现
 *
 * @author Yangjy
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class DeliveryServiceImpl extends BaseServiceImpl<DeliveryMapper, Delivery> implements DeliveryService {

    private final DeliveryItemService deliveryItemService;

    private final PurchaseOrderItemService purchaseOrderItemService;

    private final DeliveryItemSnMapper deliveryItemSnMapper;


    @Override
    public boolean removeCascadeById(Serializable id) {
        deliveryItemService.remove(Wrappers.<DeliveryItem>query().eq("delivery_id", id));
        return super.removeById(id);
    }

    @Override
    public List<Delivery> list(DeliveryQueryRequest request) {
        List<Delivery> list = this.list(Wrappers.<Delivery>query()
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getPoCode()), "po_code", request.getPoCode())
                .eq("status", DictConsts.STATUS_DELIVERY_UNFILLED)
                .orderByDesc("code")
        );
        return list;
    }

    @Override
    public IPage<Delivery> page(DeliveryQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Delivery>query()
                .eq(StringUtils.isBlank(request.getCode()), "status", DictConsts.STATUS_DELIVERY_UNFILLED)
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getPoCode()), "po_code", request.getPoCode())
                .orderByDesc("code")
        );
        return page;
    }


    @Override
    public boolean updateDeliverStatus(String id) {

        // 根据id查出发货单
        Delivery delivery = this.getById(id);
        Integer status = delivery.getStatus();

        // 发货单状态!=0
        if (status != DictConsts.STATUS_DELIVERY_UNFILLED) {
            throw new BusinessException(delivery.getCode() + "发货单已经发货");
        }

        // 根据delivery_id查询发货明细
        List<DeliveryItem> deliveryItemList = deliveryItemService.list(Wrappers.<DeliveryItem>query().eq("delivery_id", id));

        Double deliveryAmount = 0.0;
        // 根据发货明细,修改发货状态  数量  金额
        for (DeliveryItem item : deliveryItemList) {
            // 应发数量
            Double shouldSendQty = item.getShouldDeliveryQty();
            // 已发数量
            Double sendedQty = item.getDeliveredQty();
            // 本次实发数量
            Double qty = item.getQty();

            if (qty <= 0) {
                throw new BusinessException(item.getMaterialName() + "实发数量不能小于等于0");
            }
            // 应发数量 < 可发数量
            if (shouldSendQty < sendedQty + qty) {
                throw new BusinessException(MessageFormat.format(item.getMaterialName()+"本次实发数量超过可发数量,应发:{0},已发:{1},本次实发:{2}", shouldSendQty, sendedQty, qty));
            }
            // 应该数量=可发数量
            PurchaseOrderItem orderItem = new PurchaseOrderItem();
            if (shouldSendQty == sendedQty + qty) {
                //修改采购单数量  金额  状态为 2 全部发货
                orderItem.setDeliveryStatus(DictConsts.STATUS_DELIVERYED)
                        .setDeliveredQty(shouldSendQty)
                        .setDeliveredAmount(item.getSubtotalAmount())
                        .setId(item.getPoItemId());

                purchaseOrderItemService.updateById(orderItem);
                // 应该数量 > 发数量
            } else if (shouldSendQty > sendedQty + qty) {
                //修改采购单数量  金额  状态为 1 部分发货
                orderItem.setDeliveryStatus(DictConsts.STATUS_DELIVERYED)
                        .setDeliveredQty(sendedQty + qty)
                        .setDeliveredAmount(item.getPrice() * (sendedQty + qty))
                        .setId(item.getPoItemId());
                purchaseOrderItemService.updateById(orderItem);

            }
            deliveryAmount += item.getSubtotalAmount();
//          将当前发货明细加入采购单的,修改他们的已发数量
            this.deliveryItemService.update(Wrappers.<DeliveryItem>update()
                    .set("delivered_qty", orderItem.getDeliveredQty())
                    .eq("po_item_id", item.getPoItemId())
                    .ne("delivery_id", item.getDeliveryId()));
        }
        // 更新 发货单 为已经发货状态 1
        Delivery tmp = new Delivery();
        tmp.setDeliveryAmount(deliveryAmount)
                .setStatus(DictConsts.STATUS_DELIVERY_SENT)
                .setGmtDelivery(DateUtils.now())
                .setCreator(UserUtils.getUser().getUsername())

                .setId(id);

        return this.updateById(tmp);

    }

    @Override
    public boolean saveDeliverys(Delivery entity) {
        //生成单号
        String code = SerialUtils.generateCode(SerialRuleConsts.ORDER_DELIVERY_CODE);
        entity.setCode(code)
                .setStatus(DictConsts.STATUS_DELIVERY_UNFILLED)
                .setCreator(UserUtils.getUser().getId())
                .setGmtCreate(DateUtils.now());
        // 新增发货单主表
        boolean saveCount = this.save(entity);
        if (!saveCount) {
            throw new BusinessException("新增发货单失败");
        }
        // 查询出采购订单明细
        List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemService.list(Wrappers.<PurchaseOrderItem>query()
                .eq("po_id", entity.getPoId()));
        List<DeliveryItem> deliveryItemList = new ArrayList<>();

        // 采购明细添加到发货明细
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {

            try {
//                如果数量=发货数量
                if (purchaseOrderItem.getDeliveredQty().equals(purchaseOrderItem.getQty())) {
                    continue;
                }
                DeliveryItem deliveryItem = new DeliveryItem();
                BeanUtils.copyProperties(purchaseOrderItem, deliveryItem);
                deliveryItem.setDeliveryId(entity.getId())
                        .setPoItemId(purchaseOrderItem.getId())
                        .setShouldDeliveryQty(purchaseOrderItem.getQty())
                        .setDeliveredQty(purchaseOrderItem.getDeliveredQty())
                        .setQty(purchaseOrderItem.getQty() - purchaseOrderItem.getDeliveredQty())
                        .setId(null);
                deliveryItemList.add(deliveryItem);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // 批量添加
        boolean saveBatchBoo = deliveryItemService.saveBatch(deliveryItemList);
        if (!saveBatchBoo) {
            throw new BusinessException("批量新增发货单明细失败");
        }

        return true;
    }


    @Override
    public IPage<Delivery> sendPage(DeliveryQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Delivery>query()
                .eq(StringUtils.isBlank(request.getCode()), "status", DictConsts.STATUS_DELIVERY_SENT)
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getPoCode()), "po_code", request.getPoCode())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public boolean cancelDelivery(String id) {
        PurchaseOrderItem orderItem = new PurchaseOrderItem();
        Delivery delivery = this.getById(id);
//        没有发货抛出异常
        if (delivery.getStatus() != 1) {
            throw new BusinessException("发货单未发货");
        }
        List<DeliveryItem> deliveryItemList = deliveryItemService.list(Wrappers.<DeliveryItem>query()
                .eq("delivery_id", id));
        for (DeliveryItem item : deliveryItemList) {
//            应发 - 本次实发 = 0  修改为未发货
            if (item.getShouldDeliveryQty().equals(item.getQty())) {
                orderItem.setDeliveryStatus(DictConsts.STATUS_UNDELIVERY);
//                否则修改为部分发货
            } else {
                orderItem.setDeliveryStatus(DictConsts.STATUS_PART_DELIVERY);
            }

//            修改发货数量     修改状态   修改金额  撤销发货操作
            orderItem.setDeliveredQty(item.getShouldDeliveryQty() - item.getQty())
                    .setDeliveredAmount((item.getShouldDeliveryQty() - item.getQty()) * item.getPrice())
                    .setId(item.getPoItemId());
            purchaseOrderItemService.updateById(orderItem);
//              把当前明细加入发货订单的,修改 已发数量,还原之前数量
            this.deliveryItemService.update(Wrappers.<DeliveryItem>update()
                    .set("delivered_qty", item.getShouldDeliveryQty() - item.getQty())
                    .eq("po_item_id", item.getPoItemId())
                    .ne("delivery_id", item.getDeliveryId()));
        }
//        修改状态 -> 0   修改人  修改时间
        Delivery tmp = new Delivery();
        tmp.setStatus(DictConsts.STATUS_DELIVERY_UNFILLED)
                .setGmtDelivery(null)
                .setId(id);
//        刪除SN
        deliveryItemSnMapper.delete(Wrappers.<DeliveryItemSn>query()
                .eq("delivery_id",id));
//         修改主表
        return this.updateById(tmp);
    }

    @Override
    public IPage allDeliveryPage(DeliveryQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Delivery>query()
                .eq(StringUtils.isNotBlank(request.getStatus()), "status", request.getStatus())
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getPoCode()), "po_code", request.getPoCode())
                .orderByDesc("gmt_modified")
        );
        return page;
    }


}
    