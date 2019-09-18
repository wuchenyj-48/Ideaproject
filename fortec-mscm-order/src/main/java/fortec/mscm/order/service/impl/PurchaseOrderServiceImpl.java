
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;

import fortec.mscm.order.entity.PurchaseOrder;
import fortec.mscm.order.request.PurchaseOrderQueryRequest;

import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.request.PurchaseOrderItemQueryRequest;
import fortec.mscm.order.mapper.PurchaseOrderMapper;
import fortec.mscm.order.service.PurchaseOrderService;
import fortec.mscm.order.service.PurchaseOrderItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
* 采购订单 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@AllArgsConstructor
@Service
public class PurchaseOrderServiceImpl extends BaseServiceImpl<PurchaseOrderMapper, PurchaseOrder> implements PurchaseOrderService {

    private final PurchaseOrderItemService purchaseOrderItemService;

    @Override
    public boolean removeCascadeById(Serializable id) {
        purchaseOrderItemService.remove(Wrappers.<PurchaseOrderItem>query().eq("po_id", id));
        return super.removeById(id);
    }

    @Override
    public List<PurchaseOrder> list(PurchaseOrderQueryRequest request) {
        List<PurchaseOrder> list = this.list(Wrappers.<PurchaseOrder>query()
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getSupplierName()), "supplier_name", request.getSupplierName())
                .eq(request.getSupplierConfirmStatus() != null, "supplier_confirm_status", request.getSupplierConfirmStatus())
                .eq(request.getDeliveryStatus() != null, "delivery_status", request.getDeliveryStatus())
                .eq(request.getStatus() != null, "status", request.getStatus())
                .between(request.getBeginGmtCreate() != null && request.getEndGmtCreate() != null, "gmt_create", request.getBeginGmtCreate(), request.getEndGmtCreate())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<PurchaseOrder> page(PurchaseOrderQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<PurchaseOrder>query()
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getSupplierName()), "supplier_name", request.getSupplierName())
                .eq(request.getSupplierConfirmStatus() != null, "supplier_confirm_status", request.getSupplierConfirmStatus())
                .eq(request.getDeliveryStatus() != null, "delivery_status", request.getDeliveryStatus())
                .eq(request.getStatus() != null, "status", request.getStatus())
                .between(request.getBeginGmtCreate() != null && request.getEndGmtCreate() != null, "gmt_create", request.getBeginGmtCreate(), request.getEndGmtCreate())
            .orderByDesc("gmt_modified")
        );
        return page;
    }

}
    