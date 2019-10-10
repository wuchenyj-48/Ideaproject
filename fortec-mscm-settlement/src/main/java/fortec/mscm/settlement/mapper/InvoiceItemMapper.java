
package fortec.mscm.settlement.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.settlement.DTO.InvoiceItemDTO;
import fortec.mscm.settlement.VO.InvoiceItemVO;
import fortec.mscm.settlement.entity.InvoiceItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 发票单明细 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface InvoiceItemMapper extends BaseMapper<InvoiceItem> {

    /**
     * 发票单明细 关联页
     * @param page
     * @param dto
     * @return
     */
    IPage<InvoiceItemVO> pageForRelate(IPage page, @Param("dto") InvoiceItemDTO dto);

    /**
     * 发票单明细 查看页
     * @param page
     * @param dto
     * @return
     */
    IPage<InvoiceItemVO> pageForView(IPage page, @Param("dto") InvoiceItemDTO dto);
}
    