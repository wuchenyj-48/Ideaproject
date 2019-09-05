
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.model.TreeModel;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.MaterialCatalog;
import fortec.mscm.base.request.MaterialCatalogQueryRequest;
import fortec.mscm.base.service.MaterialCatalogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 商品品类 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/material_catalogs")
public class MaterialCatalogController extends BaseController {

    private MaterialCatalogService materialCatalogService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid MaterialCatalog entity) {
        boolean bSave = materialCatalogService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid MaterialCatalog entity) {
        boolean bUpdate = materialCatalogService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(MaterialCatalogQueryRequest request) {
        IPage page = materialCatalogService.pageForTree(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialCatalogQueryRequest request) {
        List<MaterialCatalog> list = materialCatalogService.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @GetMapping("/tree")
    public CommonResult tree(MaterialCatalogQueryRequest request) {
        TreeModel<MaterialCatalog> treeModel = materialCatalogService.tree(request);
        return CommonResult.ok("查询成功", treeModel.asList());
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean bRemove = materialCatalogService.deleteById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
    