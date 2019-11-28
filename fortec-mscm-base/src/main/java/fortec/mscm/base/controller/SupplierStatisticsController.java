package fortec.mscm.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.ImportResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.common.core.utils.DateUtils;
import fortec.common.core.utils.excel.ExportExcel;
import fortec.mscm.base.dto.SupplierStatisticsDTO;
import fortec.mscm.base.entity.SupplierStatistics;
import fortec.mscm.base.request.SupplierStatisticsQueryRequest;
import fortec.mscm.base.service.SupplierStatisticsService;
import fortec.mscm.base.vo.SupplierStatisticsVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: chen.wu
 * @CreateDate: 2019-11-12 10:37
 * Version:      2.4
 */
@AllArgsConstructor
@RestController
@RequestMapping("/form")
public class SupplierStatisticsController extends CrudController<SupplierStatistics,String, SupplierStatisticsService> implements ImAndExAbleController<SupplierStatisticsQueryRequest> {
  
    @GetMapping("/page")
    public PageResult page(SupplierStatisticsQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(SupplierStatisticsQueryRequest request) {
        List<SupplierStatisticsVO> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }




    /**
     * 根据医院名称获取供应商
     * @param request
     */
    @GetMapping("/page_by_name_for_hospital")
    public CommonResult pageByNameForHospital(SupplierStatisticsQueryRequest request){
        List<SupplierStatistics> list=service.pageByNameForHospital(request);
        return PageResult.ok("查询成功", list);
    }

    /** 导出 */

    @Override
    public void excelExport(SupplierStatisticsQueryRequest request) {
        String fileName = "供应商统计信息" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<SupplierStatisticsVO> list = this.service.list(request);
        try {
            (new ExportExcel("供应商统计信息", SupplierStatisticsVO.class)).setDataList(list).write(this.response(), fileName).dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 导入 */

    @Override
    public CommonResult<ImportResult> excelImport(MultipartFile file) {
        return CommonResult.ok("导入成功",this.service.excelImport(file));
    }


    /** 模板 */
    @Override
    public void excelTemplate(SupplierStatisticsQueryRequest request){
        try{
            String fileName="供应商统计导入模板.xlsx";
            new ExportExcel("供应商统计信息",SupplierStatisticsDTO.class,2).setDataList(service.list(request)).write(response(),fileName).dispose();
        }catch(Exception e){
            throw  new BusinessException("导出模板失败",e);

        }
    }




}
