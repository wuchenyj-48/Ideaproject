
package fortec.mscm.settlement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.settlement.entity.InvoiceLine;
import fortec.mscm.settlement.request.InvoiceLineQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 发票单行信息 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface InvoiceLineService extends IBaseService<InvoiceLine> {

    List<InvoiceLine> list(InvoiceLineQueryRequest request);


    IPage<InvoiceLine> page(InvoiceLineQueryRequest request);


}
    