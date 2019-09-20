
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.order.entity.Delivery;
import fortec.mscm.order.entity.DeliveryItem;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.mapper.DeliveryItemMapper;
import fortec.mscm.order.request.DeliveryItemQueryRequest;
import fortec.mscm.order.service.DeliveryItemService;
import fortec.mscm.order.service.PurchaseOrderItemService;
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

    private final PurchaseOrderItemService purchaseOrderItemService;


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
        List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemService.list(Wrappers.<PurchaseOrderItem>query()
                .eq(delivery.getPoId() != null, "delivery_id", delivery.getPoId())
                .in("delivery_status", 0, 1));
        List<DeliveryItem> deliveryItemList = new ArrayList<>();

//        采购明细添加到发货明细
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
            try {
                DeliveryItem deliveryItem = new DeliveryItem();
                BeanUtils.copyProperties(purchaseOrderItem, deliveryItem);
                deliveryItem.setDeliveryId(Long.valueOf(delivery.getId()))
                        .setPoItemId(Long.valueOf(purchaseOrderItem.getId()))
                        .setId(null);
                deliveryItemList.add(deliveryItem);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return deliveryItemList;
    }
}
    