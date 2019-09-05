
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
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
        IPage page = supplierService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * 获取当前供应商
     * @param request
     * @return
     */
    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(SupplierQueryRequest request) {
        IPage page = supplierService.pageForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(SupplierQueryRequest request) {
        List<Supplier> list = supplierService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = supplierService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    /**
     * 获取供应商，关键字搜索
     * @param request
     * @param keywords
     * @return
     */
    @GetMapping("/page_by_keywords")
    public PageResult pageByKeywords(SupplierQueryRequest request, @RequestParam(value = "keywords",required = false) String keywords) {
        IPage page = supplierService.pageByKeywords(request,keywords);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

}
    