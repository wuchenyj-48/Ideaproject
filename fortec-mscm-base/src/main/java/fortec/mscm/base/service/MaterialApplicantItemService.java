
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.MaterialApplicantItem;
import fortec.mscm.base.request.MaterialApplicantItemQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* MaterialApplicantItem service 接口
*
* @author chenchen
* @version 1.0
*/
public interface MaterialApplicantItemService extends IBaseService<MaterialApplicantItem> {

    List<MaterialApplicantItem> list(MaterialApplicantItemQueryRequest request);


    IPage<MaterialApplicantItem> page(MaterialApplicantItemQueryRequest request);

}
    