
package fortec.mscm.cert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.IBaseService;
import fortec.mscm.cert.dto.NoticeUpgradeCertDTO;
import fortec.mscm.cert.dto.NoticeUploadCertDTO;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.request.CertificateRepositoryQueryRequest;

import java.util.List;

/**
* 供应商资质仓库 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface CertificateRepositoryService extends IBaseService<CertificateRepository> {


    List<CertificateRepository> list(CertificateRepositoryQueryRequest request);


    IPage<CertificateRepository> page(CertificateRepositoryQueryRequest request);

    /**
     * 供应商资质列表页
     * @param request
     * @return
     */
    IPage<CertificateRepository> pageForSupplier(CertificateRepositoryQueryRequest request);


    /**
     * 供应商效期预警列表页
     * @param request
     * @return
     */
    IPage<CertificateRepository> pageForSupplierWarning(CertificateRepositoryQueryRequest request);

    /**
     * 供应商效期预警列表页
     * @param request
     * @return
     */
    IPage<CertificateRepository> pageForHospitalWarning(CertificateRepositoryQueryRequest request);

    List<CertificateRepository> pageForAudit(CertificateRepositoryQueryRequest request);


    /**
     * 添加供应商资质
     * @param entity
     * @return
     */
    boolean saveForSupplier(CertificateRepository entity);

    /**
     * 添加商品资质
     * @param entity
     * @return
     */
    boolean saveForMaterial(CertificateRepository entity);

    /**
     * 添加厂商资质
     * @param entity
     * @return
     */
    boolean saveForManufacturer(CertificateRepository entity);

    /**
     * 添加品类资质
     * @param entity
     * @return
     */
    boolean saveForCatalog(CertificateRepository entity);

    /**
     * 供应商资质关闭
     * @param id
     */
    void close(String id);

    /**
     * 供应商资质升级
     * @param entity
     */
    void upgrade(CertificateRepository entity);

    /**
     * 提醒升级资质文件
     * @param dto
     */
    void noticeUpgrade(NoticeUpgradeCertDTO dto);


    /**
     * 提醒上传资质文件
     * @param dto
     */
    void noticeUpload(NoticeUploadCertDTO dto);

    /**
     * 资质提交，修改状态为已提交
     * @param id
     */
    void certificateSubmit(String id);

}
    