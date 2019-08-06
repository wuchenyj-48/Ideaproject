package fortec.mscm.cert.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.vo.OverAllCatalog;
import fortec.mscm.cert.vo.OverAllManufacturer;
import fortec.mscm.cert.vo.OverAllMaterial;
import fortec.mscm.cert.vo.OverAllSupplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 全景视图 Overall对象
 *
 * @author chenchen
 * @version 1.0
 */
@Mapper
public interface OverallViewerMapper{

    /**
     * 查看全景视图-医院的所有供应商 已上传资质数量/要求上传数量
     * @param hospitalId
     * @return
     */
    List<OverAllSupplier> listSupplierCertCnt(@Param("hospitalId") String hospitalId);

    /**
     * 查看全景视图-供应商的所有厂商 已上传资质数量/要求上传数量
     * @param hospitalId
     * @return
     */
    List<OverAllManufacturer> listManufacturerCertCnt(@Param("hospitalId") String hospitalId , @Param("supplierId") String supplierId);

    /**
     * 查看全景视图-厂商的所有一级品类 已上传资质数量/要求上传数量
     * @param hospitalId
     * @return
     */
    List<OverAllCatalog> listCatalogCertCnt(@Param("hospitalId") String hospitalId , @Param("manufacturerId") String manufacturerId);

    /**
     * 查看全景视图-一级品类的所有商品 已上传资质数量/要求上传数量
     * @param hospitalId
     * @return
     */
    List<OverAllMaterial> listMaterialCertCnt(@Param("hospitalId") String hospitalId , @Param("catalogId") String catalogId);


    /**
     * 查询
     * @param page
     * @param hospitalId
     * @param targetDescribeId
     * @return
     */
    IPage<CertificateRepository> pageOverAll(IPage page,@Param("hospitalId") String hospitalId , @Param("targetDescribeId") String targetDescribeId);

}
