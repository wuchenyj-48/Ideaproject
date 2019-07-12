
package fortec.mscm.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.request.MaterialQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 商品 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface MaterialMapper extends BaseMapper<Material> {

    IPage<Material> page(IPage page, @Param("request") MaterialQueryRequest request);
}
    