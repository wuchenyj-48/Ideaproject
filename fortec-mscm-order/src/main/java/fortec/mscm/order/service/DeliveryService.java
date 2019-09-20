
package fortec.mscm.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.order.entity.Delivery;
import fortec.mscm.order.request.DeliveryQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 发货单 service 接口
*
* @author Yangjy
* @version 1.0
*/
public interface DeliveryService extends IBaseService<Delivery> {

    List<Delivery> list(DeliveryQueryRequest request);


    IPage<Delivery> page(DeliveryQueryRequest request);


    boolean saveDeliverys(Delivery entity);
}
    