
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.order.consts.DictConsts;
import fortec.mscm.order.entity.PurchaseOrder;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.mapper.PurchaseOrderItemMapper;
import fortec.mscm.order.request.PurchaseOrderItemQueryRequest;
import fortec.mscm.order.service.PurchaseOrderItemService;
import fortec.mscm.order.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Override
    public List<PurchaseOrderItem> list(PurchaseOrderItemQueryRequest request) {
        List<PurchaseOrderItem> list = this.list(Wrappers.<PurchaseOrderItem>query()
                .eq("po_id", request.getPoId())
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PurchaseOrderItem entity) {

        entity.setDeliveredQty(0.0)
                .setDeliveryStatus(DictConsts.STATUS_UNDELIVERY)
                .setDeliveredAmount(0.0)
                .setOrderQty(0.0)
                .setOrderUnit("千克");
        saveOrUpdate(entity);

        //更新订单总金额
        updateTotalAmount(entity.getPoId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchSave(PurchaseOrderItem[] children) {
        if (children == null || children.length == 0) {
            return;
        }
        for (PurchaseOrderItem item : children) {
            add(item);
        }



        /*List<Object> results = this.baseMapper.selectObjs(Wrappers.<PurchaseOrderItem>query().select("sum(subtotal_amount)").eq("po_id", children[0].getPoId()));
        if (results.size() == 0) {
            return saveOrUpdateBatch(Lists.newArrayList(children));
        }

        PurchaseOrder po = new PurchaseOrder();
        po.setTotalAmount(results.stream().mapToDouble(o -> ((BigDecimal)o).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() ).sum()).setId(children[0].getPoId());
        purchaseOrderService.updateById(po);*/
        saveOrUpdateBatch(Lists.newArrayList(children));

        //更新订单总金额
        updateTotalAmount(children[0].getPoId());
    }

    @Override
    public Double totalAmount(String poId) {
        List<PurchaseOrder> list = baseMapper.totalAmount(poId);
        return list.isEmpty() ? 0 : list.get(0).getTotalAmount();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        removeCascadeById(id);

        //更新订单总金额
        PurchaseOrderItem entity = getById(id);
        updateTotalAmount(entity.getPoId());
    }

    /**
     * 更新订单总金额
     * @param poId
     */
    public void updateTotalAmount(String poId){
        Double totalAmount = totalAmount(poId);
        PurchaseOrder po = new PurchaseOrder();
        po.setTotalAmount(totalAmount).setId(poId);
        purchaseOrderService.updateById(po);
    }
}
    