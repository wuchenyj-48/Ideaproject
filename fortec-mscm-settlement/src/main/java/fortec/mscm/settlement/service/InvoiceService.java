
package fortec.mscm.settlement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.settlement.entity.Invoice;
import fortec.mscm.settlement.request.InvoiceQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 开票单 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface InvoiceService extends IBaseService<Invoice> {

    List<Invoice> list(InvoiceQueryRequest request);


    IPage<Invoice> page(InvoiceQueryRequest request);

    /**
     * 添加开票单
     * @param entity
     */
    boolean add(Invoice entity);

}
    