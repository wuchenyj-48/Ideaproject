
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.IBaseService;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.request.MaterialQueryRequest;
import fortec.mscm.base.vo.MaterialVO;

import java.util.List;


/**
* 商品 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface MaterialService extends IBaseService<Material> {

    /**
     * 分页查询
     * @param request
     * @return
     */
    IPage<Material> page(MaterialQueryRequest request);

    List<Material> list(MaterialQueryRequest request);

    List<MaterialVO> importList(MaterialQueryRequest request);

    List<MaterialVO> exportList(MaterialQueryRequest request);

}
    