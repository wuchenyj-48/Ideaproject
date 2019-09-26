
package fortec.mscm.settlement.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fortec.mscm.settlement.entity.BillItem;
import org.apache.ibatis.annotations.Mapper;

/**
* 记账单明细 mapper对象
*
* @author hl
* @version 1.0
*/
@Mapper
public interface BillItemMapper extends BaseMapper<BillItem> {

    /**
     * 获取记账金额
     * @param billId
     * @return
     */
    Double totalAmount(String billId);

}
    