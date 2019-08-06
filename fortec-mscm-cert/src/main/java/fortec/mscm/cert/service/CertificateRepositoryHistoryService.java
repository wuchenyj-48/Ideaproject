
package fortec.mscm.cert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.CertificateRepositoryHistory;
import fortec.mscm.cert.request.CertificateRepositoryHistoryQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 资质历史版本 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface CertificateRepositoryHistoryService extends IBaseService<CertificateRepositoryHistory> {

    List<CertificateRepositoryHistory> list(CertificateRepositoryHistoryQueryRequest request);


    IPage<CertificateRepositoryHistory> page(CertificateRepositoryHistoryQueryRequest request);

}
    