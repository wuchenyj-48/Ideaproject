
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.common.core.utils.StringUtils;

import fortec.mscm.base.entity.SupplierRegist;
import fortec.mscm.base.request.SupplierRegistQueryRequest;
import fortec.mscm.base.service.SupplierRegistService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
*  controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/supplier_regists")
public class SupplierRegistController extends BaseController {

    private SupplierRegistService supplierRegistService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid SupplierRegist entity) {
        boolean bSave = supplierRegistService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid SupplierRegist entity) {
        boolean bUpdate = supplierRegistService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(SupplierRegistQueryRequest request) {
        IPage page = supplierRegistService.page(request.getPage(), Wrappers.<SupplierRegist>query()
                    .like(StringUtils.isNotBlank(request.getCompanyCode()), "company_code", request.getCompanyCode())
                    .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                    .eq(request.getIsDrug() != null, "is_drug", request.getIsDrug())
                    .eq(request.getIsConsumable() != null, "is_consumable", request.getIsConsumable())
                    .eq(request.getIsReagent() != null, "is_reagent", request.getIsReagent())
                    .eq(request.getAstatus() != null, "astatus", request.getAstatus())
                     .orderByDesc("gmt_modified")
                );

        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(SupplierRegistQueryRequest request) {
        List<SupplierRegist> list = supplierRegistService.list(Wrappers.<SupplierRegist>query().orderByDesc("gmt_modified"));
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = supplierRegistService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
    