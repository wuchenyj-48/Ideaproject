
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.model.BatchImportResult;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.common.core.utils.DateUtils;
import fortec.common.core.utils.excel.ExportExcel;
import fortec.mscm.base.dto.PackUnitDTO;
import fortec.mscm.base.entity.PackUnit;
import fortec.mscm.base.request.PackUnitQueryRequest;
import fortec.mscm.base.service.PackUnitService;
import fortec.mscm.base.vo.PackUnitVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
* 包装单位 controller
*
* @author yuntao.zhou
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/pack_units")
public class PackUnitController extends BaseController {

    private PackUnitService packUnitService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid PackUnit entity) {
        boolean bSave = packUnitService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid PackUnit entity) {
        boolean bUpdate = packUnitService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(PackUnitQueryRequest request) {
        IPage page = packUnitService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(PackUnitQueryRequest request) {
        List<PackUnit> list = packUnitService.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = packUnitService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @GetMapping( "excel/template")
    public void importFileTemplate(PackUnitQueryRequest request) {
        try {
            String fileName = "包装单位导入模板.xlsx";
            new ExportExcel("包装单位信息", PackUnitDTO.class, 2).setDataList(packUnitService.list(request)).write(response(), fileName).dispose();
        } catch (Exception e) {
            throw new BusinessException("导出模板失败", e);
        }
    }


    @GetMapping({"/excel/export"})
    public void export(PackUnitQueryRequest request) throws IOException {
        String fileName = "包装单位信息" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<PackUnit> list = this.packUnitService.list(request);
        (new ExportExcel("包装单位信息", PackUnitVO.class)).setDataList(list).write(this.response(), fileName).dispose();
    }

    @PostMapping({"/excel/import"})
    public BatchImportResult importExcel(MultipartFile file) throws IOException {
        return this.packUnitService.batchImport(file);
    }

}
    