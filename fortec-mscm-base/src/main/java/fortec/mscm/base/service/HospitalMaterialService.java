
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.HospitalMaterial;
import fortec.mscm.base.request.HospitalMaterialQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* HospitalMaterial service 接口
*
* @author chenchen
* @version 1.0
*/
public interface HospitalMaterialService extends IBaseService<HospitalMaterial> {

    List<HospitalMaterial> list(HospitalMaterialQueryRequest request);


    IPage<HospitalMaterial> page(HospitalMaterialQueryRequest request);

}
    