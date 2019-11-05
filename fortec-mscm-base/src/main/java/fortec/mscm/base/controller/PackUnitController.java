
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.exceptions.ImportException;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.ImportResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.common.core.utils.DateUtils;
import fortec.common.core.utils.excel.ExportExcel;
import fortec.mscm.base.dto.PackUnitDTO;
import fortec.mscm.base.entity.PackUnit;
import fortec.mscm.base.request.PackUnitQueryRequest;
import fortec.mscm.base.service.PackUnitService;
import fortec.mscm.base.vo.PackUnitVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
public class PackUnitController extends CrudController<PackUnit, String, PackUnitService> implements ImAndExAbleController<PackUnitQueryRequest> {


    @GetMapping("/page")
    public PageResult page(PackUnitQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(PackUnitQueryRequest request) {
        List<PackUnit> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @Override
    public void excelExport(PackUnitQueryRequest request) {
        String fileName = "包装单位信息" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<PackUnit> list = this.service.list(request);
        try {
            (new ExportExcel("包装单位信息", PackUnitVO.class)).setDataList(list).write(this.response(), fileName).dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommonResult<ImportResult> excelImport(MultipartFile file) throws ImportException {
        return CommonResult.ok("导入成功", service.excelImport(file));
    }

    @Override
    public void excelTemplate(PackUnitQueryRequest request) {
        try {
            String fileName = "包装单位导入模板.xlsx";
            new ExportExcel("包装单位信息", PackUnitDTO.class, 2).setDataList(service.list(request)).write(response(), fileName).dispose();
        } catch (Exception e) {
            throw new BusinessException("导出模板失败", e);
        }
    }
}
    