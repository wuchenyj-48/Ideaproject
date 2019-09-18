package fortec.mscm.cert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.request.OverallViewerQueryRequest;
import fortec.mscm.cert.vo.OverAllCatalog;
import fortec.mscm.cert.vo.OverAllManufacturer;
import fortec.mscm.cert.vo.OverAllMaterial;
import fortec.mscm.cert.vo.OverAllSupplier;

import java.util.List;

/**
 * 资质全景视图
 */
public interface OverallViewerService {

    /**
     * 资质全景视图-查看医院所有供应商资质 已上传资质数量、要求上传数量
     *
     * @return
     */
    List<OverAllSupplier> listSupplierCertCnt();


    /**
     * 资质全景视图-查看医院所有供应商资质 已上传资质数量、要求上传数量
     *
     * @return
     */
    List<OverAllManufacturer> listManufacturerCertCnt(String supplierId);

    /**
     * 资质全景视图-查看医院所有供应商资质 已上传资质数量、要求上传数量
     *
     * @return
     */
    List<OverAllCatalog> listCatalogCertCnt(String manufacturerId);

    /**
     * 资质全景视图-查看医院所有供应商资质 已上传资质数量、要求上传数量
     *
     * @return
     */
    List<OverAllMaterial> listMaterialCertCnt(String catalogId);

    /**
     * 全景视图查询功能
     *
     * @param request
     * @return
     */
    IPage<CertificateRepository> pageOverAll(OverallViewerQueryRequest request);


    /**
     * 资质全景视图-查看医院所有供应商资质 已上传资质数量、要求上传数量
     *
     * @return
     */
    List<OverAllSupplier> listSupplierBySupplier();

    /**
     * 资质全景视图-查看当前供应商资质 已上传资质数量、要求上传数量
     *
     * @return
     */
    List<OverAllManufacturer> listManufacturerBySupplier();

    /**
     * 查看全景视图-厂商的所有一级品类
     * @param manufacturerId
     * @return
     */
    List<OverAllCatalog> listCatalogForSupplier(String manufacturerId);

    /**
     * 查看供应商全景视图-一级品类的所有商品
     * @param catalogId
     * @return
     */
    List<OverAllMaterial> listMaterialForSupplier(String catalogId);

    /**
     * 供应商全景视图查询功能
     * @param request
     * @return
     */
    IPage<CertificateRepository> pageOverAllForSupplier(OverallViewerQueryRequest request);
}
