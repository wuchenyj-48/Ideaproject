
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.MaterialSpec;

import fortec.common.core.service.IBaseService;
import fortec.mscm.base.request.MaterialSpecQueryRequest;


/**
* 商品规格 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface MaterialSpecService extends IBaseService<MaterialSpec> {

    IPage<MaterialSpec> pageByKeywords(MaterialSpecQueryRequest request);
}
    