
package fortec.mscm.settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.core.consts.SerialRuleConsts;
import fortec.mscm.security.utils.UserUtils;
import fortec.mscm.settlement.consts.DictConsts;
import fortec.mscm.settlement.entity.Invoice;
import fortec.mscm.settlement.entity.InvoiceItem;
import fortec.mscm.settlement.entity.InvoiceLine;
import fortec.mscm.settlement.mapper.InvoiceLineMapper;
import fortec.mscm.settlement.mapper.InvoiceMapper;
import fortec.mscm.settlement.request.InvoiceQueryRequest;
import fortec.mscm.settlement.service.InvoiceItemService;
import fortec.mscm.settlement.service.InvoiceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* 开票单 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@AllArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class InvoiceServiceImpl extends BaseServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {

    private final InvoiceLineMapper invoiceLineMapper;

    private final InvoiceItemService invoiceItemService;

    @Override
    public boolean removeCascadeById(Serializable id) {
        invoiceLineMapper.delete(Wrappers.<InvoiceLine>query().eq("invoice_id", id));
        invoiceItemService.remove(Wrappers.<InvoiceItem>query().eq("invoice_id",id));
        return super.removeById(id);
    }

    @Override
    public List<Invoice> list(InvoiceQueryRequest request) {
        List<Invoice> list = this.list(Wrappers.<Invoice>query()
                .like(StringUtils.isNotBlank(request.getHospitalName()), "hospital_name", request.getHospitalName())
                .between(request.getBeginGmtMakeOut() != null && request.getEndGmtMakeOut() != null, "gmt_make_out", request.getBeginGmtMakeOut(), request.getEndGmtMakeOut())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<Invoice> page(InvoiceQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        IPage page = this.page(request.getPage(), Wrappers.<Invoice>query()
                .eq(request.getStatus() != null, "status", request.getStatus())
                .eq(StringUtils.isNotBlank(request.getSupplierId()), "supplier_id", request.getSupplierId())
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getHospitalName()), "hospital_name", request.getHospitalName())
                .between(request.getBeginGmtMakeOut() != null && request.getEndGmtMakeOut() != null, "gmt_make_out", request.getBeginGmtMakeOut(), request.getEndGmtMakeOut())
            .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public boolean add(Invoice entity) {
        entity.setCode(SerialUtils.generateCode(SerialRuleConsts.SETTLEMENT_INVOICE_CODE))
                .setSupplierId(UserUtils.getSupplierId())
                .setStatus(DictConsts.INVOICE_STATUS_UNSUBMIT)
                .setSupplierName(UserUtils.getSupplier().getName());
        return saveCascadeById(entity);

    }

    @Override
    public void audit(String id) {
        Invoice byId = getById(id);
        //是否为空
        if (byId == null){
            throw new BusinessException("数据异常");
        }

        List<InvoiceLine> list = invoiceLineMapper.selectList(Wrappers.<InvoiceLine>query().eq("invoice_id", id));
        if (list == null || list.size() == 0){
            throw new BusinessException("开票单行信息不能为空");
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBillQty() == 0){
                throw new BusinessException("记账单数量不能为0");
            }
        }
        //当前状态是否是制单状态
        if (byId.getStatus() != DictConsts.INVOICE_STATUS_UNSUBMIT){
            throw new BusinessException("当前状态不是制单状态，无法提交");
        }
        //状态修改为已审核
        Invoice invoice = new Invoice();
        invoice.setStatus(DictConsts.INVOICE_STATUS_AUDITED)
                .setAuditorName(UserUtils.getUser().getUsername())
                .setGmtAudited(new Date())
                .setId(byId.getId());
        //提交
        updateById(invoice);

    }

}
    