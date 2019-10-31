
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.model.*;
import fortec.common.core.mvc.controller.BaseController;
import fortec.common.core.utils.DateUtils;
import fortec.common.core.utils.excel.ExportExcel;
import fortec.mscm.base.dto.MaterialCatalogDTO;
import fortec.mscm.base.entity.MaterialCatalog;
import fortec.mscm.base.request.MaterialCatalogQueryRequest;
import fortec.mscm.base.service.MaterialCatalogService;
import fortec.mscm.base.vo.MaterialCatalogVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
        List<MaterialCatalog> list = materialCatalogService.list(request);

        TreeNode root = null;
        if (request.isAutoAddRoot()) {
            root = new TreeNode();
            root.setId("0");
            root.setTitle("商品分类");
        }

        TreeModel<MaterialCatalog> treeModel = new TreeModel<MaterialCatalog>(list, "name", root) {
            @Override
            protected void addExtraProperties(TreeNode node, MaterialCatalog entity) {
                node.addProperty("materialTypeCode", entity.getMaterialTypeCode());
            }
        };
        return CommonResult.ok("查询成功", treeModel.asList());
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean bRemove = materialCatalogService.deleteById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @GetMapping( "excel/template")
    public void importFileTemplate(MaterialCatalogQueryRequest request) {
        try {
            String fileName = "商品品类导入模板.xlsx";
            new ExportExcel("商品品类信息", MaterialCatalogDTO.class, 2).setDataList(materialCatalogService.list(request)).write(response(), fileName).dispose();
        } catch (Exception e) {
            throw new BusinessException("导出模板失败", e);
        }
    }


    @GetMapping({"/excel/export"})
    public void export(MaterialCatalogQueryRequest request) throws IOException {
        String fileName = "商品品类信息" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<MaterialCatalogVO> list = this.materialCatalogService.exportList(request);
        (new ExportExcel("商品品类信息", MaterialCatalogVO.class)).setDataList(list).write(this.response(), fileName).dispose();
    }

    @PostMapping({"/excel/import"})
    public BatchImportResult importExcel(MultipartFile file) throws IOException {
        return this.materialCatalogService.batchImport(file);
    }

}
    