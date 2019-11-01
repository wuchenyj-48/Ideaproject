
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.model.BatchImportResult;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.common.core.utils.DateUtils;
import fortec.common.core.utils.excel.ExportExcel;
import fortec.mscm.base.dto.MaterialDTO;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.request.MaterialQueryRequest;
import fortec.mscm.base.service.MaterialService;
import fortec.mscm.base.vo.MaterialVO;
import fortec.mscm.security.utils.UserUtils;
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
public class MaterialController extends CrudController<Material, String, MaterialService> implements ImAndExAbleController<MaterialQueryRequest> {

    @PostMapping
    public CommonResult add(@RequestBody @Valid Material entity) {
        entity.setSupplierId(UserUtils.getSupplierId());
        boolean bSave = service.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Material entity) {
        entity.setSupplierId(UserUtils.getSupplierId());
        boolean bUpdate = service.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(MaterialQueryRequest request) {
        IPage<MaterialVO> page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialQueryRequest request) {
        List<MaterialVO> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }



    @Override
    public void excelExport(MaterialQueryRequest request) throws IOException {
        String fileName = "商品信息" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<MaterialVO> list = this.service.exportList(request);
        (new ExportExcel("商品信息", MaterialVO.class)).setDataList(list).write(this.response(), fileName).dispose();
    }

    @Override
    public BatchImportResult excelImport(MultipartFile file) throws IOException {
        return this.service.batchImport(file);
    }

    @Override
    public void excelTemplate(MaterialQueryRequest request) {
        try {
            String fileName = "商品导入模板.xlsx";
            new ExportExcel("商品信息", MaterialDTO.class, 2).setDataList(service.importList(request)).write(response(), fileName).dispose();
        } catch (Exception e) {
            throw new BusinessException("导出模板失败", e);
        }
    }
}
    