
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.order.consts.DictConsts;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.mapper.PurchaseOrderItemMapper;
import fortec.mscm.order.request.PurchaseOrderItemQueryRequest;
import fortec.mscm.order.service.PurchaseOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 采购订单明细 service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@Slf4j
@Service
public class PurchaseOrderItemServiceImpl extends BaseServiceImpl<PurchaseOrderItemMapper, PurchaseOrderItem> implements PurchaseOrderItemService {

    @Override
    public List<PurchaseOrderItem> list(PurchaseOrderItemQueryRequest request) {
        List<PurchaseOrderItem> list = this.list(Wrappers.<PurchaseOrderItem>query()
                .eq("po_id",request.getPoId())
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<PurchaseOrderItem> page(PurchaseOrderItemQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<PurchaseOrderItem>query()
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public boolean add(PurchaseOrderItem entity) {
        entity.setDeliveredQty(0.0)
                .setDeliveryStatus(DictConsts.STATUS_UNDELIVERY)
                .setDeliveredAmount(0.0)
                .setOrderQty(0.0)
                .setOrderUnit("千克");
        return saveCascadeById(entity);
    }

    @Override
    public boolean batchSave(PurchaseOrderItem[] children) {
        for (PurchaseOrderItem item : children) {
            item.setDeliveredQty(0.0)
                    .setDeliveryStatus(DictConsts.STATUS_UNDELIVERY)
                    .setDeliveredAmount(0.0)
                    .setOrderQty(0.0)
                    .setOrderUnit("千克")
                    .setSubtotalAmount(0.0);
        }
        return saveOrUpdateBatch(Lists.newArrayList(children));
    }
}
    