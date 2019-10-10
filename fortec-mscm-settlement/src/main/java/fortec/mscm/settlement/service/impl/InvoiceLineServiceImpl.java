
package fortec.mscm.settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.settlement.entity.InvoiceLine;
import fortec.mscm.settlement.mapper.InvoiceLineMapper;
import fortec.mscm.settlement.request.InvoiceLineQueryRequest;
import fortec.mscm.settlement.service.InvoiceLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class InvoiceLineServiceImpl extends BaseServiceImpl<InvoiceLineMapper, InvoiceLine> implements InvoiceLineService {

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
}
    