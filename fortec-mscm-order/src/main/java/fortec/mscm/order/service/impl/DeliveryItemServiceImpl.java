
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.order.entity.Delivery;
import fortec.mscm.order.entity.DeliveryItem;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.mapper.DeliveryItemMapper;
import fortec.mscm.order.mapper.DeliveryMapper;
import fortec.mscm.order.mapper.PurchaseOrderItemMapper;
import fortec.mscm.order.request.DeliveryItemQueryRequest;
import fortec.mscm.order.service.DeliveryItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* 发货单明细 service 实现
*
* @author Yangjy
* @version 1.0
*/
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DeliveryItemServiceImpl extends BaseServiceImpl<DeliveryItemMapper, DeliveryItem> implements DeliveryItemService {

    private final PurchaseOrderItemMapper purchaseOrderItemMapper;

    private final DeliveryMapper deliveryMapper;


    @Override
    public List<DeliveryItem> list(DeliveryItemQueryRequest request) {
        List<DeliveryItem> list = this.list(Wrappers.<DeliveryItem>query()
                .eq(request.getDeliveryId() != null, "delivery_id", request.getDeliveryId())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<DeliveryItem> page(DeliveryItemQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<DeliveryItem>query()
                .eq(request.getDeliveryId() != null, "delivery_id", request.getDeliveryId())
            .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public List<DeliveryItem> surplusPurchaseOrder(Delivery delivery) {
        List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemMapper.selectList(Wrappers.<PurchaseOrderItem>query()
                .eq(delivery.getPoId() != null, "po_id", delivery.getPoId())
                .in("delivery_status", 0, 1));
        List<DeliveryItem> deliveryItemList = new ArrayList<>();

        List<Object>poItemIdList = this.listObjs(Wrappers.<DeliveryItem>query()
                .select("po_item_id")
                .eq("delivery_id", delivery.getId()));
        List<String> idStrList = new ArrayList<>();
        for (Object o : poItemIdList) {
            String poItemId = String.valueOf(o);
            idStrList.add(poItemId);
        }
//        采购明细添加到发货明细
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
            try {
                if (purchaseOrderItem.getQty().equals(purchaseOrderItem.getDeliveredQty())) {
                    continue;
                }
                boolean idExistBool = idStrList.contains(purchaseOrderItem.getId());

                if (idExistBool) {
                    continue;
                }
                DeliveryItem deliveryItem = new DeliveryItem();
                BeanUtils.copyProperties(purchaseOrderItem, deliveryItem);
                deliveryItem.setDeliveryId(delivery.getId())
                        .setShouldDeliveryQty(purchaseOrderItem.getQty())
                        .setDeliveredQty(purchaseOrderItem.getDeliveredQty())
                        .setPoItemId(purchaseOrderItem.getId())
                        .setId(null);
                deliveryItemList.add(deliveryItem);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return deliveryItemList;
    }

    @Override
    public boolean saveDeliveryItemsById(DeliveryItem entity) {
        entity.setSubtotalAmount(entity.getQty() * entity.getPrice());
        return this.save(entity);
    }

    @Override
    public boolean updateDeliveryItemsById(DeliveryItem entity) {
        entity.setSubtotalAmount(entity.getQty() * entity.getPrice());
        return  this.updateById(entity);
    }

    @Override
    public boolean saveOrUpdateBatchDtl(ArrayList<DeliveryItem> newArrayList) {
//        修改每条明细小计金额
        double deliveryAmount = 0.0;
        Delivery delivery = new Delivery();
        for (DeliveryItem item : newArrayList) {
            item.setSubtotalAmount(item.getQty() * item.getPrice());
            deliveryAmount += item.getSubtotalAmount();
            delivery.setId(item.getDeliveryId());
        }

        delivery.setDeliveryAmount(deliveryAmount);
//        修改主表发货金额
        deliveryMapper.updateById(delivery);
        return this.saveOrUpdateBatch(newArrayList);
    }
}
    