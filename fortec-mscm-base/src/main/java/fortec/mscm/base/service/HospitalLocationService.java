
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.IBaseService;
import fortec.mscm.base.entity.HospitalLocation;
import fortec.mscm.base.request.HospitalLocationQueryRequest;

import java.util.List;


/**
* 医院收货地点 service 接口
*
* @author yuntao.zhou
* @version 1.0
*/
public interface HospitalLocationService extends IBaseService<HospitalLocation> {


    List<HospitalLocation> list(HospitalLocationQueryRequest request);


    IPage<HospitalLocation> page(HospitalLocationQueryRequest request);
}
    