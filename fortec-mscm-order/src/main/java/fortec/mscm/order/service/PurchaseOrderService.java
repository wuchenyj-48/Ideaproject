
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

    /**
     * 添加订单
     * @param entity
     * @return
     */
    boolean add(PurchaseOrder entity);

    /**
     * 采购订单-提交 将订单状态 制单0改为待审核1
     * @param id
     */
    void submitOrder(String id);

    /**
     * 待审核订单-通过 将订单状态 待审核1改为供应商待确认2
     * @param id
     */
    void passOrder(String id);

    /**
     * 供应商待确认订单
     * @param request
     * @return
     */
    IPage<PurchaseOrder> pageForSupplier(PurchaseOrderQueryRequest request);

    /**
     * 供应商待确认订单-可供货 将供应商确认状态 待确认0改为确认可供货1
     * @param id
     */
    void able(String id);

    /**
     * 供应商待确认订单-可供货 将供应商确认状态 待确认0改为确认不可供货2
     * @param id
     */
    void disable(String id);

}
    