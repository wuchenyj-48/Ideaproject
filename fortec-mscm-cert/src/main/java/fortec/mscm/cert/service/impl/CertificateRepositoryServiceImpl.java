
package fortec.mscm.cert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.cert.consts.BusinessTypeConsts;
import fortec.mscm.cert.entity.Certificate;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.entity.CertificateRepositoryHistory;
import fortec.mscm.cert.mapper.CertificateRepositoryMapper;
import fortec.mscm.cert.request.CertificateRepositoryQueryRequest;
import fortec.mscm.cert.service.CertificateRepositoryHistoryService;
import fortec.mscm.cert.service.CertificateRepositoryService;
import fortec.mscm.cert.service.CertificateService;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商资质仓库 service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@Transactional(rollbackFor = Exception.class)
@Slf4j
@AllArgsConstructor
@Service
public class CertificateRepositoryServiceImpl extends BaseServiceImpl<CertificateRepositoryMapper, CertificateRepository> implements CertificateRepositoryService {

    private CertificateRepositoryHistoryService certificateRepositoryHistoryService;
    private CertificateService certificateService;


    private void assertCertUnique(String certificateId, String targetId) {
        Certificate certificate = certificateService.getById(certificateId);

        //判断所选资质是否限制唯一
        if (certificate.getLimitUnique() != 0) {
            List<CertificateRepository> list = this.list(Wrappers.<CertificateRepository>query()
                    .eq("certificate_id", certificateId)
                    .eq("supplier_id", UserUtils.getSupplierId())
                    .eq("target_describe_id", targetId));

            //查询该资质是否已经上传
            if (list.size() != 0) {
                throw new BusinessException("所选资质已经上传过，请重新选择");
            }
        }
    }

    @Override
    public List<CertificateRepository> list(CertificateRepositoryQueryRequest request) {
        return this.baseMapper.list(request);
    }

    @Override
    public IPage<CertificateRepository> page(CertificateRepositoryQueryRequest request) {
        return this.baseMapper.page(request.getPage(), request);
    }


    @Override
    public IPage<CertificateRepository> pageForSupplier(CertificateRepositoryQueryRequest request) {

        request.setTargetDescribeId(UserUtils.getSupplierId());
        return this.page(request);
    }

    @Override
    public IPage<CertificateRepository> pageForSupplierWarning(CertificateRepositoryQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.pageForWarning(request.getPage(), request);
    }

    @Override
    public IPage<CertificateRepository> pageForHospitalWarning(CertificateRepositoryQueryRequest request) {
        request.setHospitalId(UserUtils.getHospitalId());
        return this.baseMapper.pageForWarning(request.getPage(), request);
    }


    @Override
    public boolean addForSupplier(CertificateRepository entity) {
        //判断资质是否已上传
        this.assertCertUnique(entity.getCertificateId(), UserUtils.getSupplierId());
        entity.setSupplierId(UserUtils.getSupplierId())
                .setManufacturerId("0")
                .setCloseFlag(CertificateRepository.CLOSE_FLAG_NORMAL)
                .setBusinessTypeCode(BusinessTypeConsts.SUPPLIER)
                .setTargetDescribeId(UserUtils.getSupplierId())
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public boolean addForMaterial(CertificateRepository entity) {
        this.assertCertUnique(entity.getCertificateId(), entity.getTargetDescribeId());

        entity.setSupplierId(UserUtils.getSupplierId())
                .setBusinessTypeCode(BusinessTypeConsts.MATERIAL)
                .setCloseFlag(CertificateRepository.CLOSE_FLAG_NORMAL)
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public boolean addForManufacturer(CertificateRepository entity) {
        this.assertCertUnique(entity.getCertificateId(), entity.getTargetDescribeId());

        entity.setSupplierId(UserUtils.getSupplierId())
                .setCloseFlag(CertificateRepository.CLOSE_FLAG_NORMAL)
                .setBusinessTypeCode(BusinessTypeConsts.MANUFACTURER)
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public boolean addForCatalog(CertificateRepository entity) {
        this.assertCertUnique(entity.getCertificateId(), entity.getTargetDescribeId());

        entity.setSupplierId(UserUtils.getSupplierId())
                .setCloseFlag(CertificateRepository.CLOSE_FLAG_NORMAL)
                .setVersion(1);
        return this.saveOrUpdate(entity);
    }

    @Override
    public void close(String id) {
        //关闭
        CertificateRepository cr = this.getById(id);
        //当前状态是否正常
        if (cr.getCloseFlag() != CertificateRepository.CLOSE_FLAG_NORMAL) {
            throw new BusinessException("当前状态不是正常状态");
        }

        cr.setCloseFlag(CertificateRepository.CLOSE_FLAG_CLOSED);
        this.updateById(cr);

        //保存一条记录到历史表
        CertificateRepositoryHistory crh = new CertificateRepositoryHistory();
        crh.setCertificateRepositoryId(cr.getId())
                .setSupplierId(cr.getSupplierId())
                .setManufacturerId(cr.getManufacturerId())
                .setBusinessTypeCode(cr.getBusinessTypeCode())
                .setTargetDescribeId(cr.getTargetDescribeId())
                .setCertificateId(cr.getCertificateId())
                .setCertificateNo(cr.getCertificateNo())
                .setExpiryDate(cr.getExpiryDate())
                .setCertificateSign(cr.getCertificateSign())
                .setCertificateSignTo(cr.getCertificateSignTo())
                .setDocIds(cr.getDocIds())
                .setVersion(cr.getVersion());

        certificateRepositoryHistoryService.save(crh);
    }

    @Override
    public void upgrade(CertificateRepository entity) {

        CertificateRepository old = this.getById(entity.getId());
        if (old == null) {
            return;
        }


        entity.setVersion(old.getVersion() + 1);
        this.updateById(entity);

        //保存一条记录到历史表
        CertificateRepositoryHistory crh = new CertificateRepositoryHistory();
        crh
                .setVersion(old.getVersion())
                .setCertificateRepositoryId(old.getId())
                .setSupplierId(old.getSupplierId())
                .setManufacturerId(old.getManufacturerId())
                .setBusinessTypeCode(old.getBusinessTypeCode())
                .setTargetDescribeId(old.getTargetDescribeId())
                .setCertificateId(old.getCertificateId())
                .setCertificateNo(old.getCertificateNo())
                .setExpiryDate(old.getExpiryDate())
                .setCertificateSign(old.getCertificateSign())
                .setCertificateSignTo(old.getCertificateSignTo())
                .setDocIds(old.getDocIds())
        ;

        certificateRepositoryHistoryService.save(crh);
    }

}
    