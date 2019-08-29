
package fortec.mscm.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

import fortec.mscm.base.entity.SupplierApplicant;
import fortec.mscm.base.request.SupplierApplicantQueryRequest;
import fortec.mscm.base.service.SupplierApplicantService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * SupplierApplicant controller
 *
 * @author chenchen
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/supplier_applicants")
public class SupplierApplicantController extends BaseController {

    private SupplierApplicantService supplierApplicantService;

    @GetMapping("/page")
    public PageResult page(SupplierApplicantQueryRequest request) {
        IPage page = supplierApplicantService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }


    @GetMapping("/list")
    public CommonResult list(SupplierApplicantQueryRequest request) {
        List<SupplierApplicant> list = supplierApplicantService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = supplierApplicantService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    /**
     * 供应商资格申请
     * @param entity
     * @return
     */
    @PostMapping("/applicant")
    public CommonResult applicant(@RequestBody @Valid SupplierApplicant entity) {
        boolean applicant = supplierApplicantService.applicant(entity);
        return applicant ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");

    }

    /**
     * 供方资格申请提交 制单状态 修改为 提交待审核状态
     * @param id
     * @return
     */
    @PostMapping("/submit/{id}")
    public CommonResult submit(@PathVariable("id") String id){
        supplierApplicantService.submit(id);
        return CommonResult.ok("提交申请成功");
    }

    /**
     * 供方资格申请审核通过 提交待审核状态 修改为 已审核状态
     * @param id
     * @return
     */
    @PostMapping("/pass/{id}")
    public CommonResult pass(@PathVariable("id") String id){
        supplierApplicantService.pass(id);
        return CommonResult.ok("审核通过");
    }

    /**
     * 供方资格申请审核不通过 提交待审核状态 修改为 取消状态
     * @param id
     * @param auditedRemark
     * @return
     */
    @PostMapping("/cancel/{id}/{auditedRemark}")
    public CommonResult cancel(@PathVariable("id") String id,@PathVariable("auditedRemark") String auditedRemark){
        supplierApplicantService.cancel(id,auditedRemark);
        return CommonResult.ok("取消成功");
    }

    /**
     * 审核页
     * @param request
     * @return
     */
    @GetMapping("/pageAudit")
    public PageResult pageAudit(SupplierApplicantQueryRequest request) {
        IPage page = supplierApplicantService.pageAudit(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

}
