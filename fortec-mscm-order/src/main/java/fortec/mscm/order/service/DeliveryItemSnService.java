
package fortec.mscm.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.IBaseService;
import fortec.mscm.order.entity.DeliveryItemSn;
import fortec.mscm.order.request.DeliveryItemSnQueryRequest;

import java.util.List;

/**
* sn生成和查询 service 接口
*
* @author Yangjianye
* @version 1.0
*/
public interface DeliveryItemSnService extends IBaseService<DeliveryItemSn> {

    List<DeliveryItemSn> list(DeliveryItemSnQueryRequest request);


    IPage<DeliveryItemSn> page(DeliveryItemSnQueryRequest request);

    boolean saveDeliveryItemSns(String deliveryId);
}
    