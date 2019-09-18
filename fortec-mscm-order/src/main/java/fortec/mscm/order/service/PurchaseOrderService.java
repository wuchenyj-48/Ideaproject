
package fortec.mscm.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.order.entity.PurchaseOrder;
import fortec.mscm.order.request.PurchaseOrderQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 采购订单 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface PurchaseOrderService extends IBaseService<PurchaseOrder> {

    List<PurchaseOrder> list(PurchaseOrderQueryRequest request);


    IPage<PurchaseOrder> page(PurchaseOrderQueryRequest request);

}
    