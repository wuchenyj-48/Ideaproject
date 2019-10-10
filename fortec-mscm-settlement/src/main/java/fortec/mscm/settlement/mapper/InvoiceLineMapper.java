
package fortec.mscm.settlement.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fortec.mscm.settlement.entity.InvoiceLine;
import org.apache.ibatis.annotations.Mapper;

/**
* 发票单行信息 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface InvoiceLineMapper extends BaseMapper<InvoiceLine> {

}
    