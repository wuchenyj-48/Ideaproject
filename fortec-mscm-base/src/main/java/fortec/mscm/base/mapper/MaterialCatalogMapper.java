
package fortec.mscm.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.MaterialCatalog;
import fortec.mscm.base.request.MaterialCatalogQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 商品品类 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface MaterialCatalogMapper extends BaseMapper<MaterialCatalog> {

    /**
     * 品类树
     * @param request
     * @return
     */
    IPage<MaterialCatalog> pageForTree(IPage iPage,@Param("request") MaterialCatalogQueryRequest request);
}
    