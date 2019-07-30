
package fortec.mscm.cert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.request.CertificateRepositoryQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 供应商资质仓库 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface CertificateRepositoryService extends IBaseService<CertificateRepository> {

    List<CertificateRepository> list(CertificateRepositoryQueryRequest request);


    IPage<CertificateRepository> page(CertificateRepositoryQueryRequest request);

}
    