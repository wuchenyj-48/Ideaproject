
package fortec.mscm.cert.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.cert.dto.NoticeUpgradeCertDTO;
import fortec.mscm.cert.dto.NoticeUploadCertDTO;
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

    /**
     * 添加供应商资质
     * @param entity
     * @return
     */
    @PostMapping("/save_for_supplier")
    public CommonResult addForSupplier(@RequestBody @Valid CertificateRepository entity) {
        if(entity.getDocIds().size() == 0){
            return CommonResult.error("资质文件至少上传一个及以上");
        }
        boolean bSave = certificateRepositoryService.addForSupplier(entity);
        return bSave ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    /**
     * 添加品名资质
     * @param entity
     * @return
     */
    @PostMapping("/save_for_material")
    public CommonResult addForMaterial(@RequestBody @Valid CertificateRepository entity) {
        boolean bSave = certificateRepositoryService.addForMaterial(entity);
        return bSave ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    /**
     * 添加厂商资质
     * @param entity
     * @return
     */
    @PostMapping("/save_for_manufacturer")
    public CommonResult addForManufacturer(@RequestBody @Valid CertificateRepository entity) {
        boolean bSave = certificateRepositoryService.addForManufacturer(entity);
        return bSave ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    /**
     * 添加品类资质
     * @param entity
     * @return
     */
    @PostMapping("/save_for_catalog")
    public CommonResult addForCatalog(@RequestBody @Valid CertificateRepository entity) {
        boolean bSave = certificateRepositoryService.addForCatalog(entity);
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

    /**
     * 获取供应商资质
     * @param request
     * @return
     */
    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(CertificateRepositoryQueryRequest request) {
        IPage page = certificateRepositoryService.pageForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }


    /**
     * 供应商资质效期预警
     * @param request
     * @return
     */
    @GetMapping("/page_for_supplier_warning")
    public PageResult pageForSupplierWarning(CertificateRepositoryQueryRequest request) {
        IPage page = certificateRepositoryService.pageForSupplierWarning(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * 医院资质效期预警
     * @param request
     * @return
     */
    @GetMapping("/page_for_hospital_warning")
    public PageResult pageForHospitalWarning(CertificateRepositoryQueryRequest request) {
        IPage page = certificateRepositoryService.pageForHospitalWarning(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * 关闭资质 关闭状态修改为 已关闭1
     * @param id
     * @return
     */
    @PostMapping("/close/{id}")
    public CommonResult close(@PathVariable("id") String id) {
        certificateRepositoryService.close(id);
        return CommonResult.ok("关闭成功");
    }

    /**
     * 升级资质
     * @param entity
     * @return
     */
    @PostMapping("/upgrade")
    public CommonResult upgrade(@RequestBody @Valid CertificateRepository entity) {
        certificateRepositoryService.upgrade(entity);
        return CommonResult.ok("升级成功");
    }

    /**
     * 院方提醒升级资质
     * @param dto
     * @return
     */
    @PostMapping("/notice_upgrade")
    public CommonResult noticeUpgrade(@RequestBody NoticeUpgradeCertDTO dto) {
        certificateRepositoryService.noticeUpgrade(dto);
        return CommonResult.ok("提醒成功");
    }


    /**
     * 提醒上传资质
     * @param dto
     * @return
     */
    @PostMapping("/notice_upload")
    public CommonResult noticeUpload(@RequestBody NoticeUploadCertDTO dto) {
        certificateRepositoryService.noticeUpload(dto);
        return CommonResult.ok("提醒成功");
    }

    /**
     * 供方资格审核，查看资质页
     * @param request
     * @return
     */
    @GetMapping("/list_for_audit")
    public CommonResult listForAudit(CertificateRepositoryQueryRequest request){
        List<CertificateRepository> list = certificateRepositoryService.pageForAudit(request);
        return CommonResult.ok("查询成功",list);

    }

    /**
     * 资质提交，修改状态为已提交
     * @param id
     * @return
     */
    @PostMapping("certificate_submit/{id}")
    public CommonResult certificateSubmit(@PathVariable("id") String id){
        certificateRepositoryService.certificateSubmit(id);
        return CommonResult.ok("提交成功");
    }

}
