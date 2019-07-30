
package fortec.mscm.cert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.cert.entity.Certificate;
import fortec.mscm.cert.mapper.CertificateMapper;
import fortec.mscm.cert.request.CertificateQueryRequest;
import fortec.mscm.cert.service.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 预定义资质 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class CertificateServiceImpl extends BaseServiceImpl<CertificateMapper, Certificate> implements CertificateService {

    @Override
    public List<Certificate> list(CertificateQueryRequest request) {
        List<Certificate> list = this.list(Wrappers.<Certificate>query()
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                .eq(request.getNeedExpiryDate() != null, "need_expiry_date", request.getNeedExpiryDate())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<Certificate> page(CertificateQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Certificate>query()
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                .eq(request.getNeedExpiryDate() != null, "need_expiry_date", request.getNeedExpiryDate())
            .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public IPage<Certificate> pageByKeywords(CertificateQueryRequest request) {
        return this.baseMapper.pageByKeywords(request.getPage(),request);
    }
}
    