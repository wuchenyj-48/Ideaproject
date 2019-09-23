
package fortec.mscm.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fortec.mscm.order.entity.PurchaseOrder;
import fortec.mscm.order.entity.PurchaseOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 采购订单明细 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface PurchaseOrderItemMapper extends BaseMapper<PurchaseOrderItem> {

    List<PurchaseOrder> totalAmount(@Param("poId") String poId);
}
    