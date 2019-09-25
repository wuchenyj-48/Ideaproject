
package fortec.mscm.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fortec.mscm.order.entity.PurchaseOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 采购订单明细 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface PurchaseOrderItemMapper extends BaseMapper<PurchaseOrderItem> {

    Double totalAmount(@Param("poId") String poId);
}
    