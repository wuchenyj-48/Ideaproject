
package fortec.mscm.cert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.mapper.CertificateRepositoryMapper;
import fortec.mscm.cert.request.CertificateRepositoryQueryRequest;
import fortec.mscm.cert.service.CertificateRepositoryService;
import fortec.mscm.security.utils.UserUtils;
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
        return this.baseMapper.list(request);
    }

    @Override
    public IPage<CertificateRepository> page(CertificateRepositoryQueryRequest request) {
        return this.baseMapper.page(request.getPage(),request);
    }


    @Override
    public IPage<CertificateRepository> pageForSupplier(CertificateRepositoryQueryRequest request) {

        request.setTargetDescribeId(UserUtils.getSupplierId());
        return this.page(request);
    }

    @Override
    public IPage<CertificateRepository> pageForWarning(CertificateRepositoryQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.pageForWarning(request.getPage(),request);
    }

    @Override
    public IPage<CertificateRepository> pageForAll(CertificateRepositoryQueryRequest request) {
        return this.baseMapper.pageForAll(request.getPage(),request);
    }

    @Override
    public boolean addForSupplier(CertificateRepository entity) {
        entity.setSupplierId(UserUtils.getSupplierId())
                .setManufacturerId("0")
                .setCloseFlag(CertificateRepository.CLOSE_FLAG_NORMAL)
                .setBusinessTypeCode("10")
                .setTargetDescribeId(UserUtils.getSupplierId())
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public boolean addForMaterial(CertificateRepository entity) {
        entity.setSupplierId(UserUtils.getSupplierId())
                .setBusinessTypeCode("20")
                .setCloseFlag(CertificateRepository.CLOSE_FLAG_NORMAL)
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public boolean addForManufacturer(CertificateRepository entity) {
        entity.setSupplierId(UserUtils.getSupplierId())
                .setCloseFlag(CertificateRepository.CLOSE_FLAG_NORMAL)
                .setBusinessTypeCode("30")
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public boolean addForCatalog(CertificateRepository entity) {
        entity.setSupplierId(UserUtils.getSupplierId())
                .setCloseFlag(CertificateRepository.CLOSE_FLAG_NORMAL)
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public void close(String id) {
        CertificateRepository entity = this.getById(id);
        //当前状态是否正常
        if (entity.getCloseFlag() != CertificateRepository.CLOSE_FLAG_NORMAL){
            throw new BusinessException("当前状态不是正常状态");
        }
        //修改状态为已关闭
        entity.setCloseFlag(CertificateRepository.CLOSE_FLAG_CLOSED);
        this.updateById(entity);
    }

    @Override
    public boolean saveCascadeById(CertificateRepository entity) {
        return this.save(entity);
    }
}
    