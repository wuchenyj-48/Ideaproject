
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.core.consts.SerialRuleConsts;
import fortec.mscm.order.consts.DictConsts;
import fortec.mscm.order.entity.PurchaseOrder;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.mapper.PurchaseOrderMapper;
import fortec.mscm.order.request.PurchaseOrderQueryRequest;
import fortec.mscm.order.service.PurchaseOrderItemService;
import fortec.mscm.order.service.PurchaseOrderService;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static fortec.mscm.order.consts.DictConsts.STATUS_UNPASS;

/**
 * 采购订单 service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
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
        if(UserUtils.getUser().isHospital()) {
            request.setHospitalId(UserUtils.getHospitalId());
        }
        List<PurchaseOrder> list = this.list(Wrappers.<PurchaseOrder>query()
                .eq(StringUtils.isNotBlank(request.getHospitalId()),"hospital_id",request.getHospitalId())
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
        request.setHospitalId(UserUtils.getHospitalId());
        IPage page = this.page(request.getPage(), Wrappers.<PurchaseOrder>query()
                .eq(StringUtils.isNotBlank(request.getHospitalId()),"hospital_id",request.getHospitalId())
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

    @Override
    public boolean add(PurchaseOrder entity) {

        entity.setCode(SerialUtils.generateCode(SerialRuleConsts.ORDER_PO_CODE))
                .setTotalAmount(0.0)
                .setBuyerName(UserUtils.getUser().getUsername())
                .setSupplierConfirmStatus(DictConsts.STATUS_UNCONFIRM)
                .setDeliveryStatus(DictConsts.STATUS_UNDELIVERY)
                .setStatus(DictConsts.STATUS_UNSUBMIT)
                .setIsClosed(DictConsts.STATUS_NO)
                .setSource(DictConsts.STATUS_MANUAL)
                .setHospitalId(UserUtils.getHospitalId())
                .setHospitalName(UserUtils.getHospital().getName());
        return saveOrUpdate(entity);
    }

    @Override
    public void submitOrder(String id) {
        PurchaseOrder byId = getById(id);

        if (byId == null) {
            return;
        }

        //当前订单状态是否是制单状态
        if (byId.getStatus() != DictConsts.STATUS_UNSUBMIT) {
            throw new BusinessException("当前订单状态不是制单状态，无法提交");
        }

        //明细是否为空
        List<PurchaseOrderItem> list = purchaseOrderItemService.list(Wrappers.<PurchaseOrderItem>query().eq("po_id", id));
        if (list.isEmpty()) {
            throw new BusinessException("采购订单明细为空，不允许提交");
        }

        //制单状态修改为待审核状态
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStatus(STATUS_UNPASS)
                .setId(id);

        updateById(purchaseOrder);

    }

    @Override
    public void passOrder(String id) {
        //获取本条数据
        PurchaseOrder byId = getById(id);
        if (byId == null){
            return;
        }
        //当前订单状态是否是待审核状态
        if (byId.getStatus() != STATUS_UNPASS){
            throw new BusinessException("当前订单状态不是待审核状态，不允许通过");
        }
        //更新状态，添加审核人，审核时间
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStatus(DictConsts.STATUS_SUPPLIER_UNCONFIRM)
                .setAuditorName(UserUtils.getUser().getUsername())
                .setGmtAudited(new Date())
                .setId(id);
        //提交
        updateById(purchaseOrder);
    }

    @Override
    public IPage<PurchaseOrder> pageForSupplier(PurchaseOrderQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        IPage page = this.page(request.getPage(), Wrappers.<PurchaseOrder>query()
                .notIn("status",DictConsts.STATUS_UNSUBMIT+DictConsts.STATUS_UNPASS)
                .eq(StringUtils.isNotBlank(request.getSupplierId()),"supplier_id",request.getSupplierId())
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getHospitalName()), "hospital_name", request.getHospitalName())
                .eq(request.getSupplierConfirmStatus() != null, "supplier_confirm_status", request.getSupplierConfirmStatus())
                .eq(request.getDeliveryStatus() != null, "delivery_status", request.getDeliveryStatus())
                .eq(request.getStatus() != null, "status", request.getStatus())
                .between(request.getBeginGmtCreate() != null && request.getEndGmtCreate() != null, "gmt_create", request.getBeginGmtCreate(), request.getEndGmtCreate())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public void able(String id) {
        //获取本条数据
        PurchaseOrder byId = getById(id);
        if (byId == null){
            return;
        }
        //判断当前状态是否待确认
        if (byId.getSupplierConfirmStatus() != DictConsts.STATUS_UNCONFIRM){
            throw new BusinessException("当前订单供应商确认状态不是待确认状态，不允许确认");
        }
        //更新状态
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplierConfirmStatus(DictConsts.STATUS_CONFIRMED_YES)
                .setStatus(DictConsts.STATUS_SUPPLIER_UNDELIVERY)
                .setId(id);
        //提交
        updateById(purchaseOrder);
    }

    @Override
    public void disable(String id) {
        //获取本条数据
        PurchaseOrder byId = getById(id);
        if (byId == null){
            return;
        }
        //判断当前状态是否待确认
        if (byId.getSupplierConfirmStatus() != DictConsts.STATUS_UNCONFIRM){
            throw new BusinessException("当前订单供应商确认状态不是待确认状态，不允许确认");
        }
        //更新状态
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplierConfirmStatus(DictConsts.STATUS_CONFIRMED_NO)
                .setStatus(DictConsts.STATUS_COMPLETE)
                .setId(id);
        //提交
        updateById(purchaseOrder);
    }


   /**
    *
    * @Description: 查询未发货订单和采购订单
    *
    * @param request
    * @Author: Yang.jianye
    * @Date: 2019/9/23
    * @return: com.baomidou.mybatisplus.core.metadata.IPage<fortec.mscm.order.entity.PurchaseOrder>
    */
    @Override
    public IPage<PurchaseOrder> pageForDelivery(PurchaseOrderQueryRequest request) {

        request.setSupplierId(UserUtils.getSupplierId());
        IPage page = this.page(request.getPage(), Wrappers.<PurchaseOrder>query()
                .eq(StringUtils.isNotBlank(request.getSupplierId()), "supplier_id", request.getSupplierId())
                .in("delivery_status", DictConsts.STATUS_UNDELIVERY,DictConsts.STATUS_PART_DELIVERY)
                .orderByDesc("gmt_modified")
        );
        return page;
    }
}
    