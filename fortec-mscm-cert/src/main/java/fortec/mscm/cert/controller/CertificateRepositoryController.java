
package fortec.mscm.cert.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.cert.consts.CommonConsts;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.request.CertificateRepositoryQueryRequest;
import fortec.mscm.cert.service.CertificateRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 供应商资质仓库 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/certificate_repositorys")
public class CertificateRepositoryController extends BaseController {

    private CertificateRepositoryService certificateRepositoryService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid CertificateRepository entity) {
        boolean bSave = certificateRepositoryService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid CertificateRepository entity) {
        boolean bUpdate = certificateRepositoryService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(CertificateRepositoryQueryRequest request) {
        IPage page = certificateRepositoryService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(CertificateRepositoryQueryRequest request) {
        List<CertificateRepository> list = certificateRepositoryService.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = certificateRepositoryService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @PostMapping("/save_for_supplier")
    public CommonResult addForSupplier(@RequestBody @Valid CertificateRepository entity) {
        entity.setSupplierId(CommonConsts.SUPPLIER_ID)
                .setBusinessTypeCode("10")
                .setTargetDescribeId(CommonConsts.SUPPLIER_ID)
                .setDocIds("1")
                .setVersion(1);
        boolean bSave = certificateRepositoryService.saveOrUpdate(entity);
        return bSave ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @PostMapping("/save_for_material")
    public CommonResult addForMaterial(@RequestBody @Valid CertificateRepository entity) {
        entity.setSupplierId(CommonConsts.SUPPLIER_ID)
                .setBusinessTypeCode("20")
                .setVersion(1);
        boolean bSave = certificateRepositoryService.saveOrUpdate(entity);
        return bSave ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @PostMapping("/save_for_manufacturer")
    public CommonResult addForManufacturer(@RequestBody @Valid CertificateRepository entity) {
        entity.setSupplierId(CommonConsts.SUPPLIER_ID)
                .setBusinessTypeCode("30")
                .setVersion(1);
        boolean bSave = certificateRepositoryService.saveOrUpdate(entity);
        return bSave ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid CertificateRepository[] children) {

        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }

        boolean bSuccess = certificateRepositoryService.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }

    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(CertificateRepositoryQueryRequest request) {
        request.setTargetDescribeId(CommonConsts.SUPPLIER_ID);
        IPage page = certificateRepositoryService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }
}
