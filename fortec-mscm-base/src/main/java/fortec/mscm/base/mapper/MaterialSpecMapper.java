
package fortec.mscm.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.MaterialSpec;
import fortec.mscm.base.request.MaterialSpecQueryRequest;
import fortec.mscm.base.vo.MaterialSpecVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 商品规格 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface MaterialSpecMapper extends BaseMapper<MaterialSpec> {

    IPage<MaterialSpecVO> page(IPage page, @Param("request") MaterialSpecQueryRequest request);

    List<MaterialSpecVO> list(@Param("request") MaterialSpecQueryRequest request);
}
    