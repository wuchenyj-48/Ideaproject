
package fortec.mscm.cert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.CertificateHospitalBusiness;
import fortec.mscm.cert.request.CertificateHospitalBusinessQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 院方业务资质定义 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface CertificateHospitalBusinessService extends IBaseService<CertificateHospitalBusiness> {

    List<CertificateHospitalBusiness> list(CertificateHospitalBusinessQueryRequest request);

    IPage<CertificateHospitalBusiness> page(CertificateHospitalBusinessQueryRequest request);

}
    