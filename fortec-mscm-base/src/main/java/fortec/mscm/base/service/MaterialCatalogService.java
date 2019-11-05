
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.ITreeService;
import fortec.mscm.base.entity.MaterialCatalog;
import fortec.mscm.base.request.MaterialCatalogQueryRequest;
import fortec.mscm.base.vo.MaterialCatalogVO;

import java.util.List;


/**
* 商品品类 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface MaterialCatalogService extends ITreeService<MaterialCatalog> {

    /**
     * 根据id删除品类
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     * 品类树
     * @param request
     * @return
     */
    IPage pageForTree(MaterialCatalogQueryRequest request);

    List<MaterialCatalog> list(MaterialCatalogQueryRequest request);

    List<MaterialCatalogVO> exportList(MaterialCatalogQueryRequest request);
}
    