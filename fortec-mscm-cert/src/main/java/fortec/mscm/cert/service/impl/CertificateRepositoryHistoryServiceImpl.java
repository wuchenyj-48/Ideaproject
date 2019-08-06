
package fortec.mscm.cert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;

import fortec.mscm.cert.entity.CertificateRepositoryHistory;
import fortec.mscm.cert.mapper.CertificateRepositoryHistoryMapper;
import fortec.mscm.cert.request.CertificateRepositoryHistoryQueryRequest;
import fortec.mscm.cert.service.CertificateRepositoryHistoryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
* 资质历史版本 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class CertificateRepositoryHistoryServiceImpl extends BaseServiceImpl<CertificateRepositoryHistoryMapper, CertificateRepositoryHistory> implements CertificateRepositoryHistoryService {

    @Override
    public List<CertificateRepositoryHistory> list(CertificateRepositoryHistoryQueryRequest request) {
        List<CertificateRepositoryHistory> list = this.list(Wrappers.<CertificateRepositoryHistory>query()
                .eq(StringUtils.isNotBlank(request.getCertificateRepositoryId()), "certificate_repository_id", request.getCertificateRepositoryId())
                .eq(StringUtils.isNotBlank(request.getBusinessTypeCode()), "business_type_code", request.getBusinessTypeCode())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<CertificateRepositoryHistory> page(CertificateRepositoryHistoryQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<CertificateRepositoryHistory>query()
                .eq(StringUtils.isNotBlank(request.getCertificateRepositoryId()), "certificate_repository_id", request.getCertificateRepositoryId())
                .eq(StringUtils.isNotBlank(request.getBusinessTypeCode()), "business_type_code", request.getBusinessTypeCode())
            .orderByDesc("gmt_modified")
        );
        return page;
    }
}
    