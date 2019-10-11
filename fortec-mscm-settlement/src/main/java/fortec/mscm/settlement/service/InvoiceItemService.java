
package fortec.mscm.settlement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.settlement.DTO.InvoiceItemDTO;
import fortec.mscm.settlement.VO.InvoiceItemVO;
import fortec.mscm.settlement.entity.InvoiceItem;
import fortec.mscm.settlement.request.InvoiceItemQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 发票单明细 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface InvoiceItemService extends IBaseService<InvoiceItem> {

    List<InvoiceItem> list(InvoiceItemQueryRequest request);


    IPage<InvoiceItem> page(InvoiceItemDTO invoiceItemDTO);

    /**
     * 发票单明细 关联页
     * @param dto
     * @return
     */
    IPage<InvoiceItemVO> pageForRelate(InvoiceItemDTO dto);

    /**
     * 发票单明细 查看页
     * @param dto
     * @return
     */
    IPage<InvoiceItemVO> pageForView(InvoiceItemDTO dto);

}
    