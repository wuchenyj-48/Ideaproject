
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

    /**
     * @param id delivery_id
     * @Description: 更新发货状态 0 ->  1
     * @return: boolean
     */
    boolean updateDeliverStatus(String id);

    /**
     * @param entity 发货订单 bean
     * @Description: 保存发货单主表及明细
     * @return: boolean
     */
    boolean saveDeliverys(Delivery entity);


    IPage<Delivery> sendPage(DeliveryQueryRequest request);

    /**
     * @param id
     * @Description: 取消发货
     * @return: boolean
     */
    boolean cancelDelivery(String id);

    IPage allDeliveryPage(DeliveryQueryRequest request);
}
    