package fortec.mscm.cert.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.request.OverallViewerQueryRequest;
import fortec.mscm.cert.service.OverallViewerService;
import fortec.mscm.cert.vo.OverAllCatalog;
import fortec.mscm.cert.vo.OverAllManufacturer;
import fortec.mscm.cert.vo.OverAllMaterial;
import fortec.mscm.cert.vo.OverAllSupplier;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 资质全景视图 controller
 *
 * @author chenchen
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/overall_viewer")
public class OverallViewerController extends BaseController {

    private final OverallViewerService overallViewerService;

    @GetMapping("/list_supplier_cert_cnt")
    public CommonResult listSupplierCertCnt(){
        List<OverAllSupplier> list = overallViewerService.listSupplierCertCnt();
        return CommonResult.ok("查询成功",list);
    }

    @GetMapping("/list_supplier_by_supplier")
    public CommonResult listCurrentSupplierCertCnt(){
        List<OverAllSupplier> list = overallViewerService.listSupplierBySupplier();
        return CommonResult.ok("查询成功",list);
    }

    @GetMapping("/list_manufacturer_cert_cnt/{supplierId}")
    public CommonResult listManufacturerCertCnt(@PathVariable("supplierId") String supplierId){
        List<OverAllManufacturer> list = overallViewerService.listManufacturerCertCnt(supplierId);
        return CommonResult.ok("查询成功",list);
    }

    @GetMapping("/list_catalog_cert_cnt/{manufacturerId}")
    public CommonResult listCatalogCertCnt(@PathVariable("manufacturerId") String manufacturerId){
        List<OverAllCatalog> list = overallViewerService.listCatalogCertCnt(manufacturerId);
        return CommonResult.ok("查询成功",list);
    }

    @GetMapping("/list_material_cert_cnt/{catalogId}")
    public CommonResult listMaterialCertCnt(@PathVariable("catalogId") String catalogId){
        List<OverAllMaterial> list = overallViewerService.listMaterialCertCnt(catalogId);
        return CommonResult.ok("查询成功",list);
    }

    /**
     * 全景视图查询
     * @param request
     * @return
     */
    @GetMapping("/page_over_all")
    public PageResult pageOverAll(OverallViewerQueryRequest request) {
        IPage<CertificateRepository> page = overallViewerService.pageOverAll(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list_manufacturer_by_supplier")
    public CommonResult listManufacturerBySupplier(){
        List<OverAllManufacturer> list = overallViewerService.listManufacturerBySupplier();
        return CommonResult.ok("查询成功",list);
    }

    @GetMapping("/list_catalog_by_supplier/{manufacturerId}")
    public CommonResult listCatalogForSupplier(@PathVariable("manufacturerId") String manufacturerId){
        List<OverAllCatalog> list = overallViewerService.listCatalogForSupplier(manufacturerId);
        return CommonResult.ok("查询成功",list);
    }

    @GetMapping("/list_material_by_supplier/{catalogId}")
    public CommonResult listMaterialForSupplier(@PathVariable("catalogId") String catalogId){
        List<OverAllMaterial> list = overallViewerService.listMaterialForSupplier(catalogId);
        return CommonResult.ok("查询成功",list);
    }

    /**
     * 全景视图查询
     * @param request
     * @return
     */
    @GetMapping("/page_over_all_for_supplier")
    public PageResult pageOverAllForSupplier(OverallViewerQueryRequest request) {
        IPage<CertificateRepository> page = overallViewerService.pageOverAllForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }
}
