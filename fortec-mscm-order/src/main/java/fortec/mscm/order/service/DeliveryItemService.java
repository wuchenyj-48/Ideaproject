
package fortec.mscm.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.IBaseService;
import fortec.mscm.order.entity.Delivery;
import fortec.mscm.order.entity.DeliveryItem;
import fortec.mscm.order.request.DeliveryItemQueryRequest;

import java.util.List;

/**
* 发货单明细 service 接口
*
* @author Yangjy
* @version 1.0
*/
public interface DeliveryItemService extends IBaseService<DeliveryItem> {

    List<DeliveryItem> list(DeliveryItemQueryRequest request);


    IPage<DeliveryItem> page(DeliveryItemQueryRequest request);

    List<DeliveryItem> surplusPurchaseOrder(Delivery delivery);
}
    