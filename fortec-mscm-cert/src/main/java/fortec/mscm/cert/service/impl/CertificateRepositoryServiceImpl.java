
package fortec.mscm.cert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.mapper.CertificateRepositoryMapper;
import fortec.mscm.cert.request.CertificateRepositoryQueryRequest;
import fortec.mscm.cert.service.CertificateRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 供应商资质仓库 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class CertificateRepositoryServiceImpl extends BaseServiceImpl<CertificateRepositoryMapper, CertificateRepository> implements CertificateRepositoryService {

    @Override
    public List<CertificateRepository> list(CertificateRepositoryQueryRequest request) {
       /* List<CertificateRepository> list = this.list(Wrappers.<CertificateRepository>query()
                .eq(StringUtils.isNotBlank(request.getBusinessTypeCode()), "business_type_code", request.getBusinessTypeCode())
                .like(StringUtils.isNotBlank(request.getCertificateId()), "certificate_id", request.getCertificateId())
                .like(StringUtils.isNotBlank(request.getCertificateNo()), "certificate_no", request.getCertificateNo())
                .between(request.getBeginExpiryDate() != null && request.getEndExpiryDate() != null, "expiry_date", request.getBeginExpiryDate(), request.getEndExpiryDate())
            .orderByDesc("gmt_modified")
        );*/
        return this.baseMapper.list(request);
    }

    @Override
    public IPage<CertificateRepository> page(CertificateRepositoryQueryRequest request) {
        /*IPage page = this.page(request.getPage(), Wrappers.<CertificateRepository>query()
                .eq(StringUtils.isNotBlank(request.getBusinessTypeCode()), "business_type_code", request.getBusinessTypeCode())
                .like(StringUtils.isNotBlank(request.getCertificateId()), "certificate_id", request.getCertificateId())
                .like(StringUtils.isNotBlank(request.getCertificateNo()), "certificate_no", request.getCertificateNo())
                .between(request.getBeginExpiryDate() != null && request.getEndExpiryDate() != null, "expiry_date", request.getBeginExpiryDate(), request.getEndExpiryDate())
            .orderByDesc("gmt_modified")
        );*/
        return this.baseMapper.page(request.getPage(),request);
    }

    @Override
    public boolean saveCascadeById(CertificateRepository entity) {

        return this.save(entity);
    }
}
    