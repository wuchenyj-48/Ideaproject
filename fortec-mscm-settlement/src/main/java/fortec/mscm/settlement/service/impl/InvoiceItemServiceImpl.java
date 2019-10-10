
package fortec.mscm.settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.security.utils.UserUtils;
import fortec.mscm.settlement.DTO.InvoiceItemDTO;
import fortec.mscm.settlement.VO.InvoiceItemVO;
import fortec.mscm.settlement.entity.InvoiceItem;
import fortec.mscm.settlement.mapper.InvoiceItemMapper;
import fortec.mscm.settlement.request.InvoiceItemQueryRequest;
import fortec.mscm.settlement.service.InvoiceItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* 发票单明细 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class InvoiceItemServiceImpl extends BaseServiceImpl<InvoiceItemMapper, InvoiceItem> implements InvoiceItemService {

    @Override
    public List<InvoiceItem> list(InvoiceItemQueryRequest request) {
        List<InvoiceItem> list = this.list(Wrappers.<InvoiceItem>query()
                .eq(request.getInvoiceLineId() != null, "invoice_line_id", request.getInvoiceLineId())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<InvoiceItem> page(InvoiceItemDTO invoiceItemDTO) {
        IPage page = this.page(invoiceItemDTO.getPage(), Wrappers.<InvoiceItem>query()
                .eq(invoiceItemDTO.getInvoiceLineId() != null, "invoice_line_id", invoiceItemDTO.getInvoiceLineId())
            .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public IPage<InvoiceItemVO> pageForRelate(InvoiceItemDTO dto) {
        dto.setSupplierId(UserUtils.getSupplierId());
        IPage<InvoiceItemVO> page = baseMapper.pageForRelate(dto.getPage(), dto);
        return page;
    }

    @Override
    public IPage<InvoiceItemVO> pageForView(InvoiceItemDTO dto) {
        IPage<InvoiceItemVO> page = baseMapper.pageForView(dto.getPage(), dto);
        return page;
    }
}
    