
package fortec.mscm.cert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.cert.entity.CertificateHospitalBusiness;
import fortec.mscm.cert.mapper.CertificateHospitalBusinessMapper;
import fortec.mscm.cert.request.CertificateHospitalBusinessQueryRequest;
import fortec.mscm.cert.service.CertificateHospitalBusinessService;
import fortec.mscm.security.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 院方业务资质定义 service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@Slf4j
@Service
public class CertificateHospitalBusinessServiceImpl extends BaseServiceImpl<CertificateHospitalBusinessMapper, CertificateHospitalBusiness> implements CertificateHospitalBusinessService {

    @Override
    public List<CertificateHospitalBusiness> list(CertificateHospitalBusinessQueryRequest request) {
        List<CertificateHospitalBusiness> list = this.list(Wrappers.<CertificateHospitalBusiness>query()
                .like(StringUtils.isNotBlank(request.getHospitalId()), "hospital_id", request.getHospitalId())
                .like(StringUtils.isNotBlank(request.getBusinessTypeCode()), "business_type_code", request.getBusinessTypeCode())
                .like(StringUtils.isNotBlank(request.getCertificateId()), "certificate_id", request.getCertificateId())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<CertificateHospitalBusiness> page(CertificateHospitalBusinessQueryRequest request) {
        return this.baseMapper.page(request.getPage(),request);
    }


    @Override
    public boolean save(CertificateHospitalBusiness entity) {
        entity.setHospitalId(UserUtils.getHospitalId());
        return super.save(entity);
    }

}
    