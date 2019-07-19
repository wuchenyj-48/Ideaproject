
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.MaterialApplicant;
import fortec.mscm.base.request.MaterialApplicantQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* MaterialApplicant service 接口
*
* @author chenchen
* @version 1.0
*/
public interface MaterialApplicantService extends IBaseService<MaterialApplicant> {

    List<MaterialApplicant> list(MaterialApplicantQueryRequest request);


    IPage<MaterialApplicant> page(MaterialApplicantQueryRequest request);

    boolean saveHospital(MaterialApplicant entity);

}
    