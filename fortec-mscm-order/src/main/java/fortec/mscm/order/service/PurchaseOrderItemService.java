
package fortec.mscm.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.IBaseService;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.request.PurchaseOrderItemQueryRequest;

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

    /**
     * 添加明细
     * @param entity
     * @return
     */
    void add(PurchaseOrderItem entity);

    /**
     * 批量保存
     * @param children
     * @return
     */
    void batchSave(PurchaseOrderItem[] children);

    /**
     * 计算订单总金额
     * @param poId
     * @return
     */
    Double totalAmount(String poId);

    /**
     * 删除一条订单明细
     * @param id
     */
    void delete(String id);

    /**
     * 修改一条订单明细
     * @param entity
     */
    void update(PurchaseOrderItem entity);
}
    