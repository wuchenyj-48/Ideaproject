
package fortec.mscm.cert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.IBaseService;
import fortec.mscm.cert.entity.Certificate;
import fortec.mscm.cert.request.CertificateQueryRequest;

import java.util.List;

/**
* 预定义资质 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface CertificateService extends IBaseService<Certificate> {

    List<Certificate> list(CertificateQueryRequest request);


    IPage<Certificate> page(CertificateQueryRequest request);

    /**
     * 关键词搜索资质
     * @param request
     * @return
     */
    IPage<Certificate> pageByKeywords(CertificateQueryRequest request);
}
    