
package fortec.mscm.settlement.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.settlement.entity.BillItem;
import fortec.mscm.settlement.request.BillItemQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    IPage<BillItem> pageForRelate(IPage page,@Param("request") BillItemQueryRequest request);

}
    