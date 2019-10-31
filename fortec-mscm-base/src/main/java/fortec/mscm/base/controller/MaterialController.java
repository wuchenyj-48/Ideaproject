
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.model.BatchImportResult;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.common.core.utils.DateUtils;
import fortec.common.core.utils.excel.ExportExcel;
import fortec.mscm.base.dto.MaterialDTO;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.request.MaterialQueryRequest;
import fortec.mscm.base.service.MaterialService;
import fortec.mscm.base.vo.MaterialVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
* 商品 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/materials")
public class MaterialController extends BaseController {

    private MaterialService materialService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid Material entity) {
        boolean bSave = materialService.saveOrUpdate(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Material entity) {
        boolean bUpdate = materialService.saveOrUpdate(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(MaterialQueryRequest request) {
        IPage<Material> page = materialService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialQueryRequest request) {
        List<Material> list = materialService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = materialService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @GetMapping( "excel/template")
    public void importFileTemplate(MaterialQueryRequest request) {
        try {
            String fileName = "商品导入模板.xlsx";
            new ExportExcel("商品信息", MaterialDTO.class, 2).setDataList(materialService.importList(request)).write(response(), fileName).dispose();
        } catch (Exception e) {
            throw new BusinessException("导出模板失败", e);
        }
    }

    @GetMapping({"/excel/export"})
    public void export(MaterialQueryRequest request) throws IOException {
        String fileName = "商品信息" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<MaterialVO> list = this.materialService.exportList(request);
        (new ExportExcel("商品信息", MaterialVO.class)).setDataList(list).write(this.response(), fileName).dispose();
    }

    @PostMapping({"/excel/import"})
    public BatchImportResult importExcel(MultipartFile file) throws IOException {
        return this.materialService.batchImport(file);
    }

}
    