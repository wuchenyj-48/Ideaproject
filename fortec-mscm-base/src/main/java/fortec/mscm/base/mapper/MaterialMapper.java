
package fortec.mscm.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.request.MaterialQueryRequest;
import fortec.mscm.base.vo.MaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 商品 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface MaterialMapper extends BaseMapper<Material> {

    IPage<MaterialVO> page(IPage page, @Param("request") MaterialQueryRequest request);

    List<MaterialVO> list(@Param("request") MaterialQueryRequest request);

    List<MaterialVO> importList(@Param("request") MaterialQueryRequest request);

    List<MaterialVO> exportList(@Param("request") MaterialQueryRequest request);
}
    