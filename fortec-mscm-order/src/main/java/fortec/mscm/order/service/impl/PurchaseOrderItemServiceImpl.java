
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.order.consts.DictConsts;
import fortec.mscm.order.entity.PurchaseOrder;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.mapper.PurchaseOrderItemMapper;
import fortec.mscm.order.mapper.PurchaseOrderMapper;
import fortec.mscm.order.request.PurchaseOrderItemQueryRequest;
import fortec.mscm.order.service.PurchaseOrderItemService;
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
    private PurchaseOrderMapper purchaseOrderMapper;

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
        Double totalAmount = totalAmount(entity.getPoId());
        PurchaseOrder po = new PurchaseOrder();
        po.setTotalAmount(totalAmount).setId(entity.getPoId());
        purchaseOrderMapper.updateById(po);
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

        saveOrUpdateBatch(Lists.newArrayList(children));

        //更新订单总金额
        Double totalAmount = totalAmount(children[0].getPoId());
        PurchaseOrder po = new PurchaseOrder();
        po.setTotalAmount(totalAmount).setId(children[0].getPoId());
        purchaseOrderMapper.updateById(po);
    }

    @Override
    public Double totalAmount(String poId) {
        Double amount = baseMapper.totalAmount(poId);
        return amount == null ? 0 : amount;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        PurchaseOrderItem entity = getById(id);
        String poId = entity.getPoId();
        removeCascadeById(id);

        //更新订单总金额

        Double totalAmount = totalAmount(poId);
        PurchaseOrder po = new PurchaseOrder();
        po.setTotalAmount(totalAmount).setId(poId);

        purchaseOrderMapper.updateById(po);
    }

    @Override
    public void update(PurchaseOrderItem entity) {
        updateCascadeById(entity);
        //更新订单总金额
        Double totalAmount = totalAmount(entity.getPoId());
        PurchaseOrder po = new PurchaseOrder();
        po.setTotalAmount(totalAmount).setId(entity.getPoId());
        purchaseOrderMapper.updateById(po);
    }

}
    