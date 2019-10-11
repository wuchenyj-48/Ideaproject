
package fortec.mscm.settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.core.consts.SerialRuleConsts;
import fortec.mscm.security.utils.UserUtils;
import fortec.mscm.settlement.entity.Invoice;
import fortec.mscm.settlement.entity.InvoiceLine;
import fortec.mscm.settlement.mapper.InvoiceLineMapper;
import fortec.mscm.settlement.mapper.InvoiceMapper;
import fortec.mscm.settlement.request.InvoiceQueryRequest;
import fortec.mscm.settlement.service.InvoiceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
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

    @Override
    public boolean removeCascadeById(Serializable id) {
        invoiceLineMapper.delete(Wrappers.<InvoiceLine>query().eq("invoice_id", id));
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
        IPage page = this.page(request.getPage(), Wrappers.<Invoice>query()
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
                .setSupplierName(UserUtils.getSupplier().getName());
        return saveCascadeById(entity);

    }

}
    