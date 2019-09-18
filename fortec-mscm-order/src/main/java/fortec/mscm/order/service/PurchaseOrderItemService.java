
package fortec.mscm.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.request.PurchaseOrderItemQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 采购订单明细 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface PurchaseOrderItemService extends IBaseService<PurchaseOrderItem> {

    List<PurchaseOrderItem> list(PurchaseOrderItemQueryRequest request);


    IPage<PurchaseOrderItem> page(PurchaseOrderItemQueryRequest request);

}
    