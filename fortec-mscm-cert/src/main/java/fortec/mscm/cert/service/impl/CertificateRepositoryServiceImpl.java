
package fortec.mscm.cert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.global.GlobalDictService;
import fortec.common.core.msg.domain.SceneMessage;
import fortec.common.core.msg.enums.ReceiverType;
import fortec.common.core.msg.provider.MsgPushProvider;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.DateUtils;
import fortec.mscm.base.feign.vo.CatalogVO;
import fortec.mscm.base.feign.vo.ManufacturerVO;
import fortec.mscm.base.feign.vo.MaterialVO;
import fortec.mscm.base.feign.vo.SupplierVO;
import fortec.mscm.cert.consts.BusinessTypeConsts;
import fortec.mscm.cert.dto.NoticeUpgradeCertDTO;
import fortec.mscm.cert.dto.NoticeUploadCertDTO;
import fortec.mscm.cert.entity.Certificate;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.entity.CertificateRepositoryHistory;
import fortec.mscm.cert.mapper.CertificateRepositoryMapper;
import fortec.mscm.cert.request.CertificateRepositoryQueryRequest;
import fortec.mscm.cert.service.CertificateRepositoryHistoryService;
import fortec.mscm.cert.service.CertificateRepositoryService;
import fortec.mscm.cert.service.CertificateService;
import fortec.mscm.core.consts.MsgConsts;
import fortec.mscm.feign.clients.CatalogClient;
import fortec.mscm.feign.clients.ManufacturerClient;
import fortec.mscm.feign.clients.MaterialClient;
import fortec.mscm.feign.clients.SupplierClient;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
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

    private final CertificateRepositoryHistoryService certificateRepositoryHistoryService;

    private final CertificateService certificateService;

    private final SupplierClient supplierClient;

    private final MsgPushProvider msgPushProvider;

    private final ManufacturerClient manufacturerClient;

    private final CatalogClient catalogClient;

    private final MaterialClient materialClient;

    private final GlobalDictService globalDictService;

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
        return this.baseMapper.pageForSupplierWarning(request.getPage(), request);
    }

    @Override
    public IPage<CertificateRepository> pageForHospitalWarning(CertificateRepositoryQueryRequest request) {
        request.setHospitalId(UserUtils.getHospitalId());
        return this.baseMapper.pageForHospitalWarning(request.getPage(), request);
    }

    @Override
    public List<CertificateRepository> pageForAudit(CertificateRepositoryQueryRequest request) {
       return this.baseMapper.pageForAudit(request);

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

    /**
     * 提醒资质升级
     * @param dto
     */
    @Override
    public void noticeUpgrade(NoticeUpgradeCertDTO dto) {

        CertificateRepository repository = this.getById(dto.getRepositoryId());

        if (repository == null) {
            throw new BusinessException("数据错误");
        }

        Certificate certificate = certificateService.getById(repository.getCertificateId());
        SupplierVO supplierVO = supplierClient.findById(repository.getSupplierId());
        ManufacturerVO manufacturerVO = manufacturerClient.findById(repository.getManufacturerId());

        /**
         * 发送提醒升级通知文件
         */
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("business_type", globalDictService.getDictLabel( "cert_business_type",repository.getBusinessTypeCode(),"unknown"));
        params.put("cert_name", certificate.getName());
        params.put("supplier_name", supplierVO.getName());
        params.put("send_date", DateUtils.format(new Date(), "yyyy-MM-dd"));

        String scene = MsgConsts.SCENE_NOTICE_UPGRADE_CERT;
        switch (dto.getBusinessTypeCode()) {
            case BusinessTypeConsts.SUPPLIER:
                break;
            case BusinessTypeConsts.MANUFACTURER:
                params.put("manufacturer_name", manufacturerVO.getName());
                break;
            case BusinessTypeConsts.CATALOG_LEVEL1:
                CatalogVO catalogVO = catalogClient.findById(repository.getTargetDescribeId());
                params.put("manufacturer_name", manufacturerVO.getName());
                params.put("catalog_name", catalogVO.getName());
                break;
            case BusinessTypeConsts.MATERIAL:
                params.put("manufacturer_name", manufacturerVO.getName());
                MaterialVO materialVO = materialClient.findById(repository.getTargetDescribeId());
                params.put("material_name", materialVO.getMaterialName());
                break;
        }

        SceneMessage sm = new SceneMessage();
        sm.setSceneCode(scene).setReceiver(supplierVO.getCode())
                .setReceiverType(ReceiverType.USER).params(params);
        msgPushProvider.push(sm);
    }


    /**
     * 提醒上传
     * @param dto
     */
    @Override
    public void noticeUpload(NoticeUploadCertDTO dto) {

        Certificate certificate = certificateService.getById(dto.getCertificateId());

        SupplierVO supplierVO = supplierClient.findById(dto.getSupplierId());



        /**
         * 发送提醒上传通知文件
         */
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("business_type", globalDictService.getDictLabel( "cert_business_type",dto.getBusinessTypeCode(),"unknown"));
        params.put("supplier_name", supplierVO.getName());
        params.put("cert_name", certificate.getName());
        params.put("send_date", DateUtils.format(new Date(), "yyyy-MM-dd"));

        ManufacturerVO manufacturerVO;

        String scene = MsgConsts.SCENE_NOTICE_UPLOAD_CERT;
        switch (dto.getBusinessTypeCode()) {
            case BusinessTypeConsts.SUPPLIER:
                break;
            case BusinessTypeConsts.MANUFACTURER:
                manufacturerVO = manufacturerClient.findById(dto.getManufacturerId());
                params.put("manufacturer_name", manufacturerVO.getName());
                break;
            case BusinessTypeConsts.CATALOG_LEVEL1:
                CatalogVO catalogVO = catalogClient.findById(dto.getTargetDescribeId());
                params.put("catalog_name", catalogVO.getName());
                manufacturerVO = manufacturerClient.findById(dto.getManufacturerId());
                params.put("manufacturer_name", manufacturerVO.getName());
                break;
            case BusinessTypeConsts.MATERIAL:
                MaterialVO materialVO = materialClient.findById(dto.getTargetDescribeId());
                params.put("material_name", materialVO.getMaterialName());
                manufacturerVO = manufacturerClient.findById(dto.getManufacturerId());
                params.put("manufacturer_name", manufacturerVO.getName());
                break;
        }

        SceneMessage message = new SceneMessage();
        message.setSceneCode(scene).setReceiver(supplierVO.getCode())
                .setReceiverType(ReceiverType.USER).params(params);

        msgPushProvider.push(message);

    }

}
    