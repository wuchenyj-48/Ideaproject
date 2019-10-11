
package fortec.mscm.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.MaterialSpec;
import fortec.mscm.base.request.MaterialSpecQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 商品规格 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface MaterialSpecMapper extends BaseMapper<MaterialSpec> {

    IPage<MaterialSpec> page(IPage page, @Param("request") MaterialSpecQueryRequest request);

}
    