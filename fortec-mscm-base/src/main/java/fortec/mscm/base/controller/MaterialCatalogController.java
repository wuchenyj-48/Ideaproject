
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.model.*;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
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
public class MaterialCatalogController extends CrudController<MaterialCatalog, String, MaterialCatalogService> implements ImAndExAbleController<MaterialCatalogQueryRequest> {

    @GetMapping("/page")
    public PageResult page(MaterialCatalogQueryRequest request) {
        IPage page = service.pageForTree(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialCatalogQueryRequest request) {
        List<MaterialCatalog> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @GetMapping("/tree")
    public CommonResult tree(MaterialCatalogQueryRequest request) {
        List<MaterialCatalog> list = service.list(request);

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
        boolean bRemove = service.deleteById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @Override
    public void excelExport(MaterialCatalogQueryRequest request) {
        String fileName = "商品品类信息" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<MaterialCatalogVO> list = this.service.exportList(request);

        try {
            (new ExportExcel("商品品类信息", MaterialCatalogVO.class)).setDataList(list).write(this.response(), fileName).dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommonResult<ImportResult> excelImport(MultipartFile file) {
        return CommonResult.ok("导入成功",this.service.excelImport(file));
    }

    @Override
    public void excelTemplate(MaterialCatalogQueryRequest request) {
        try {
            String fileName = "商品品类导入模板.xlsx";
            new ExportExcel("商品品类信息", MaterialCatalogDTO.class, 2).setDataList(service.list(request)).write(response(), fileName).dispose();
        } catch (Exception e) {
            throw new BusinessException("导出模板失败", e);
        }
    }
}
    