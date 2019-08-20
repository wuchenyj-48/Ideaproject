package fortec.mscm.cert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.mapper.OverallViewerMapper;
import fortec.mscm.cert.request.OverallViewerQueryRequest;
import fortec.mscm.cert.service.OverallViewerService;
import fortec.mscm.cert.vo.OverAllCatalog;
import fortec.mscm.cert.vo.OverAllManufacturer;
import fortec.mscm.cert.vo.OverAllMaterial;
import fortec.mscm.cert.vo.OverAllSupplier;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName OverallViewSeriveImpl
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/2 17:04
 */
@Slf4j
@AllArgsConstructor
@Service
public class OverallViewSeriveImpl implements OverallViewerService {

    private final OverallViewerMapper overallViewerMapper;


    @Override
    public List<OverAllSupplier> listSupplierCertCnt() {
        return overallViewerMapper.listSupplierCertCnt(UserUtils.getHospitalId());
    }

    @Override
    public List<OverAllManufacturer> listManufacturerCertCnt(String supplierId) {
        return overallViewerMapper.listManufacturerCertCnt(UserUtils.getHospitalId(), supplierId);
    }

    @Override
    public List<OverAllCatalog> listCatalogCertCnt(String manufacturerId) {
        return overallViewerMapper.listCatalogCertCnt(UserUtils.getHospitalId(), manufacturerId);
    }

    @Override
    public List<OverAllMaterial> listMaterialCertCnt(String catalogId) {
        return overallViewerMapper.listMaterialCertCnt(UserUtils.getHospitalId(), catalogId);
    }

    @Override
    public IPage<CertificateRepository> pageOverAll(OverallViewerQueryRequest request) {
        request.setHospitalId(UserUtils.getHospitalId());
        return overallViewerMapper.pageOverAll(request.getPage(), request);
    }
}
