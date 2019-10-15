
package fortec.mscm.settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.settlement.entity.InvoiceItem;
import fortec.mscm.settlement.entity.InvoiceLine;
import fortec.mscm.settlement.mapper.InvoiceItemMapper;
import fortec.mscm.settlement.mapper.InvoiceLineMapper;
import fortec.mscm.settlement.request.InvoiceLineQueryRequest;
import fortec.mscm.settlement.service.InvoiceLineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
* 发票单行信息 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class InvoiceLineServiceImpl extends BaseServiceImpl<InvoiceLineMapper, InvoiceLine> implements InvoiceLineService {

    private final InvoiceItemMapper invoiceItemMapper;

    @Override
    public boolean removeCascadeById(Serializable id) {
        invoiceItemMapper.delete(Wrappers.<InvoiceItem>query().eq("invoice_line_id",id));
        return super.removeById(id);
    }

    @Override
    public List<InvoiceLine> list(InvoiceLineQueryRequest request) {
        List<InvoiceLine> list = this.list(Wrappers.<InvoiceLine>query()
                .eq("invoice_id",request.getInvoiceId())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<InvoiceLine> page(InvoiceLineQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<InvoiceLine>query()
            .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public boolean saveCascadeById(InvoiceLine entity) {
        entity.setBillQty(0);
        return super.saveCascadeById(entity);
    }
}
    