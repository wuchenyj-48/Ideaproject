
package fortec.mscm.settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.security.utils.UserUtils;
import fortec.mscm.settlement.DTO.InvoiceItemDTO;
import fortec.mscm.settlement.VO.InvoiceItemVO;
import fortec.mscm.settlement.entity.InvoiceItem;
import fortec.mscm.settlement.entity.InvoiceLine;
import fortec.mscm.settlement.mapper.InvoiceItemMapper;
import fortec.mscm.settlement.mapper.InvoiceLineMapper;
import fortec.mscm.settlement.request.InvoiceItemQueryRequest;
import fortec.mscm.settlement.service.InvoiceItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
@AllArgsConstructor
public class InvoiceItemServiceImpl extends BaseServiceImpl<InvoiceItemMapper, InvoiceItem> implements InvoiceItemService {

    private final InvoiceLineMapper invoiceLineMapper;

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
        return baseMapper.pageForRelate(dto.getPage(), dto);
    }

    @Override
    public IPage<InvoiceItemVO> pageForView(InvoiceItemDTO dto) {
        return baseMapper.pageForView(dto.getPage(), dto);
    }

    @Override
    public boolean batchDelete(String[] ids, String invoiceLineId) {
        InvoiceLine byId = invoiceLineMapper.selectById(invoiceLineId);
        //修改关联记账单数量
        int billQty =byId.getBillQty()-ids.length;
        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setBillQty(billQty).setId(invoiceLineId);
        invoiceLineMapper.updateById(invoiceLine);
        return removeByIds(Lists.newArrayList(ids));
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<InvoiceItem> entityList) {

        String invoiceLineId = String.valueOf(entityList.stream().findFirst().get().getInvoiceLineId());
        InvoiceLine byId = invoiceLineMapper.selectById(invoiceLineId);
        int billQty = byId.getBillQty() + entityList.size();
        InvoiceLine invoiceLine = new InvoiceLine();

        //修改关联记账单数量
        invoiceLine.setBillQty(billQty).setId(invoiceLineId);
        invoiceLineMapper.updateById(invoiceLine);
        return super.saveOrUpdateBatch(entityList);
    }

}
    