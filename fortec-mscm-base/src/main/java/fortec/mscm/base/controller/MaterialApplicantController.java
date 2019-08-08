
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

import fortec.mscm.base.entity.MaterialApplicant;
import fortec.mscm.base.request.MaterialApplicantQueryRequest;
import fortec.mscm.base.service.MaterialApplicantService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* MaterialApplicant controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/material_applicants")
public class MaterialApplicantController extends BaseController {

    private MaterialApplicantService materialApplicantService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid MaterialApplicant entity) {
        boolean bSave = materialApplicantService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid MaterialApplicant entity) {
        boolean bUpdate = materialApplicantService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(MaterialApplicantQueryRequest request) {
        IPage page = materialApplicantService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/pageAudit")
    public PageResult pageAudit(MaterialApplicantQueryRequest request) {
        IPage page = materialApplicantService.pageAudit(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialApplicantQueryRequest request) {
        List<MaterialApplicant> list = materialApplicantService.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = materialApplicantService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @PostMapping("/saveHospital")
    public CommonResult saveHospital(@RequestBody @Valid MaterialApplicant entity) {
        boolean bSave = materialApplicantService.saveHospital(entity);
        return bSave ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @PostMapping("/submit/{id}")
    public CommonResult submit(@PathVariable("id") String id){
        materialApplicantService.submit(id);
        return CommonResult.ok("提交成功");
    }

    @PostMapping("/pass/{id}")
    public CommonResult pass(@PathVariable("id") String id){
        materialApplicantService.pass(id);
        return CommonResult.ok("审核通过");
    }

    @PostMapping("/cancel/{id}/{reason}")
    public CommonResult cancel(@PathVariable("id") String id,@PathVariable("reason") String reason){
        materialApplicantService.cancel(id,reason);
        return CommonResult.ok("取消成功");
    }

}
    