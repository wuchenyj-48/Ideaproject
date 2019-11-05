
package fortec.mscm.base.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.excel.handler.ExcelDictHandlerImpl;
import fortec.common.core.exceptions.ExportException;
import fortec.common.core.exceptions.ImportException;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.ImportResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.common.core.utils.BeanUtils;
import fortec.common.core.utils.DateUtils;
import fortec.mscm.base.dto.MaterialDTO;
import fortec.mscm.base.dto.MaterialSpecDTO;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.request.MaterialQueryRequest;
import fortec.mscm.base.request.MaterialSpecQueryRequest;
import fortec.mscm.base.service.MaterialService;
import fortec.mscm.base.service.MaterialSpecService;
import fortec.mscm.base.vo.MaterialSpecVO;
import fortec.mscm.base.vo.MaterialVO;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
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

    private final MaterialSpecService materialSpecService;

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


//
//    @Override
//    public void excelExport(MaterialQueryRequest request) {
//        String fileName = "商品信息" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".xlsx";
//        List<MaterialVO> list = this.service.exportList(request);
//        try {
//            (new ExportExcel("商品信息", MaterialVO.class)).setDataList(list).write(this.response(), fileName).dispose();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public CommonResult<ImportResult> excelImport(MultipartFile file) {
//        return CommonResult.ok("导入成功",this.service.excelImport(file));
//    }
//
//    @Override
//    public void excelTemplate(MaterialQueryRequest request) {
//        try {
//            String fileName = "商品导入模板.xlsx";
//            new ExportExcel("商品信息", MaterialDTO.class, 2).setDataList(service.importList(request)).write(response(), fileName).dispose();
//        } catch (Exception e) {
//            throw new BusinessException("导出模板失败", e);
//        }
//    }

    @Override
    public void excelExport(MaterialQueryRequest request) {
        String fileName = "商品信息" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<MaterialVO> list = service.list(request);


        ExportParams params = new ExportParams("商品信息", "商品信息", ExcelType.XSSF);
        params.setDictHandler(ExcelDictHandlerImpl.getInstance());
        if (Boolean.TRUE.equals(request.getExportSpecs())) {
            for (MaterialVO materialVO : list) {
                List<MaterialSpecVO> specs = materialSpecService.list(new MaterialSpecQueryRequest().setMaterialId(materialVO.getId()));
                materialVO.setSpecs(specs);
            }
        } else {
            params.setExclusions(new String[]{"规格"});
        }
        try {
            this.setDownloadParam(fileName);
            Workbook workbook = ExcelExportUtil.exportExcel(params, MaterialVO.class, list);
            workbook.write(this.response().getOutputStream());
            workbook.close();
        } catch (IOException var6) {
            throw new ExportException("导出异常", var6);
        }
    }

    @Override
    public void excelTemplate(MaterialQueryRequest request) {
        String fileName = "商品导入模板.xlsx";
        request.setCurrPage(1);
        request.setPageSize(10);
        List list = service.page(request).getRecords();

        try {
            List<MaterialDTO> dtos = Lists.newArrayListWithCapacity(list.size());
            BeanUtils.copyProperties(list, dtos, MaterialDTO.class);

            ExportParams params = new ExportParams("商品导入模板", "商品信息", ExcelType.XSSF);
            params.setDictHandler(ExcelDictHandlerImpl.getInstance());
            params.setExclusions(new String[]{"错误消息", "错误行号"});

            if (Boolean.TRUE.equals(request.getExportSpecs())) {
                for (MaterialDTO materialDTO : dtos) {
                    List<MaterialSpecVO> specs = materialSpecService.list(new MaterialSpecQueryRequest().setMaterialId(materialDTO.getId()));

                    List<MaterialSpecDTO> specDTOS = Lists.newArrayListWithCapacity(specs.size());
                    BeanUtils.copyProperties(specs, specDTOS, MaterialSpecDTO.class);

                    materialDTO.setSpecs(specDTOS);
                }
            } else {
                params.setExclusions(new String[]{"错误消息", "错误行号", "规格"});
            }
            this.setDownloadParam(fileName);
            Workbook workbook = ExcelExportUtil.exportExcel(params, MaterialDTO.class, dtos);
            workbook.write(this.response().getOutputStream());
            workbook.close();
        } catch (IOException var7) {
            throw new ExportException("导出异常", var7);
        }
    }

    public CommonResult<ImportResult> excelImport(MultipartFile file) throws ImportException {
        return CommonResult.ok("导入成功", service.excelImport(file));
    }

}
    