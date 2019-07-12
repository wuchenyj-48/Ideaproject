
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.Supplier;
import fortec.mscm.base.request.SupplierQueryRequest;
import fortec.mscm.base.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 供应商 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/suppliers")
public class SupplierController extends BaseController {

    private SupplierService supplierService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid Supplier entity) {
        boolean bSave = supplierService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Supplier entity) {
        boolean bUpdate = supplierService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(SupplierQueryRequest request) {
        IPage page = supplierService.page(request.getPage(), Wrappers.<Supplier>query()
                    .like(StringUtils.isNotBlank(request.getCompanyCode()), "company_code", request.getCompanyCode())
                    .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                    .eq(request.getIsDrug() != null, "is_drug", request.getIsDrug())
                    .eq(request.getIsConsumable() != null, "is_consumable", request.getIsConsumable())
                    .eq(request.getIsReagent() != null, "is_reagent", request.getIsReagent())
                     .orderByDesc("gmt_modified")
                );

        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(SupplierQueryRequest request) {
        List<Supplier> list = supplierService.list(Wrappers.<Supplier>query().orderByDesc("gmt_modified"));
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = supplierService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @GetMapping("/page_by_keywords")
    public PageResult page(SupplierQueryRequest request, @RequestParam(value = "keywords",required = false) String keywords) {
        IPage page = supplierService.page(request.getPage(), Wrappers.<Supplier>query()
                .like(StringUtils.isNotBlank(keywords), "company_code", keywords)
                .or()
                .like(StringUtils.isNotBlank(keywords), "code", keywords)
                .or()
                .like(StringUtils.isNotBlank(keywords), "name", keywords)
                .orderByDesc("gmt_modified")
        );

        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

}
    