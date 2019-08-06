
package fortec.mscm.cert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.cert.consts.CommonConsts;
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
    public IPage<CertificateRepository> pageForSupplier(CertificateRepositoryQueryRequest request) {
        request.setTargetDescribeId(CommonConsts.SUPPLIER_ID);
        return this.page(request);
    }

    @Override
    public IPage<CertificateRepository> pageForWarning(CertificateRepositoryQueryRequest request) {
        request.setSupplierId(CommonConsts.SUPPLIER_ID);
        return this.baseMapper.pageForWarning(request.getPage(),request);
    }

    @Override
    public IPage<CertificateRepository> pageForAll(CertificateRepositoryQueryRequest request) {
        return this.baseMapper.pageForAll(request.getPage(),request);
    }

    @Override
    public boolean addForSupplier(CertificateRepository entity) {
        entity.setSupplierId(CommonConsts.SUPPLIER_ID)
                .setManufacturerId("0")
                .setCloseFlag(CertificateRepository.NORMAL)
                .setBusinessTypeCode("10")
                .setTargetDescribeId(CommonConsts.SUPPLIER_ID)
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public boolean addForMaterial(CertificateRepository entity) {
        entity.setSupplierId(CommonConsts.SUPPLIER_ID)
                .setBusinessTypeCode("20")
                .setCloseFlag(CertificateRepository.NORMAL)
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public boolean addForManufacturer(CertificateRepository entity) {
        entity.setSupplierId(CommonConsts.SUPPLIER_ID)
                .setCloseFlag(CertificateRepository.NORMAL)
                .setBusinessTypeCode("30")
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public boolean addForCatalog(CertificateRepository entity) {
        entity.setSupplierId(CommonConsts.SUPPLIER_ID)
                .setCloseFlag(CertificateRepository.NORMAL)
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public void close(String id) {
        CertificateRepository entity = this.getById(id);
        //当前状态是否正常
        if (entity.getCloseFlag() != CertificateRepository.NORMAL){
            throw new BusinessException("当前状态不是正常状态");
        }
        //修改状态为已关闭
        entity.setCloseFlag(CertificateRepository.CLOSED);
        this.updateById(entity);
    }

    @Override
    public boolean saveCascadeById(CertificateRepository entity) {
        return this.save(entity);
    }
}
    