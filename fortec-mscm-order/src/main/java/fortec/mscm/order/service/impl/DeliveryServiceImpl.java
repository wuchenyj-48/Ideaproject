
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.core.consts.SerialRuleConsts;
import fortec.mscm.order.entity.Delivery;
import fortec.mscm.order.entity.DeliveryItem;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.mapper.DeliveryMapper;
import fortec.mscm.order.request.DeliveryQueryRequest;
import fortec.mscm.order.service.DeliveryItemService;
import fortec.mscm.order.service.DeliveryService;
import fortec.mscm.order.service.PurchaseOrderItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
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
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<Delivery> page(DeliveryQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Delivery>query()
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getPoCode()), "po_code", request.getPoCode())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public boolean saveDeliverys(Delivery entity) {

        String code = SerialUtils.generateCode(SerialRuleConsts.ORDER_DELIVERY_CODE);
        entity.setCode(code);
//        新增发货单主表
        boolean saveCount = this.save(entity);
        if (!saveCount) {
            throw new BusinessException("新增发货单失败");
        }
//         查询出采购订单明细
        List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemService.list(Wrappers.<PurchaseOrderItem>query()
                .eq("po_id", entity.getPoId()));
        List<DeliveryItem> deliveryItemList = new ArrayList<>();

//        采购明细添加到发货明细
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
            try {
                DeliveryItem deliveryItem = new DeliveryItem();
                BeanUtils.copyProperties(purchaseOrderItem, deliveryItem);
                deliveryItem.setDeliveryId(Long.valueOf(entity.getId()))
                        .setPoItemId(Long.valueOf(purchaseOrderItem.getId()))
                        .setId(null);
                deliveryItemList.add(deliveryItem);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
//        批量添加
        boolean saveBatchBoo = deliveryItemService.saveBatch(deliveryItemList);
        if (!saveBatchBoo) {
            throw new BusinessException("批量新增发货单明细失败");
        }

        return true;
    }

}
    