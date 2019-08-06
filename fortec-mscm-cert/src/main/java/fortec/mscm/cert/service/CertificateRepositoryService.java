
package fortec.mscm.cert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.request.CertificateRepositoryQueryRequest;

import fortec.common.core.service.IBaseService;

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
    IPage<CertificateRepository> pageForWarning(CertificateRepositoryQueryRequest request);

    /**
     * 全景查看页面
     * @param request
     * @return
     */
    IPage<CertificateRepository> pageForAll(CertificateRepositoryQueryRequest request);


    /**
     * 添加供应商资质
     * @param entity
     * @return
     */
    boolean addForSupplier(CertificateRepository entity);

    /**
     * 添加商品资质
     * @param entity
     * @return
     */
    boolean addForMaterial(CertificateRepository entity);

    /**
     * 添加厂商资质
     * @param entity
     * @return
     */
    boolean addForManufacturer(CertificateRepository entity);

    /**
     * 添加厂商资质
     * @param entity
     * @return
     */
    boolean addForCatalog(CertificateRepository entity);

    /**
     * 供应商资质关闭
     * @param id
     */
    void close(String id);

}
    