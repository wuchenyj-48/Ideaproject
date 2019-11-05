
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
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
public class MaterialApplicantController extends CrudController<MaterialApplicant, String, MaterialApplicantService> implements ImAndExAbleController<MaterialApplicantQueryRequest> {

    @GetMapping("/page")
    public PageResult page(MaterialApplicantQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * 供货申请审核页
     * @param request
     * @return
     */
    @GetMapping("/pageAudit")
    public PageResult pageAudit(MaterialApplicantQueryRequest request) {
        IPage page = service.pageAudit(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialApplicantQueryRequest request) {
        List<MaterialApplicant> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    /**
     * 供应商选择医院并保存
     * @param entity
     * @return
     */
    @PostMapping("/saveHospital")
    public CommonResult saveHospital(@RequestBody @Valid MaterialApplicant entity) {
        boolean bSave = service.saveHospital(entity);
        return bSave ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    /**
     * 提交供货申请，制单状态 修改为 已提交状态
     * @param id
     * @return
     */
    @PostMapping("/submit/{id}")
    public CommonResult submit(@PathVariable("id") String id){
        service.submit(id);
        return CommonResult.ok("提交成功");
    }

    /**
     * 供货申请审核通过 已提交状态 修改为 已审核状态
     * @param id
     * @return
     */
    @PostMapping("/pass/{id}")
    public CommonResult pass(@PathVariable("id") String id){
        service.pass(id);
        return CommonResult.ok("审核通过");
    }

    /**
     * 供货申请审核不通过 已提交状态 修改为 取消状态
     * @param id
     * @param reason
     * @return
     */
    @PostMapping("/cancel/{id}/{reason}")
    public CommonResult cancel(@PathVariable("id") String id,@PathVariable("reason") String reason){
        service.cancel(id,reason);
        return CommonResult.ok("取消成功");
    }

}
    