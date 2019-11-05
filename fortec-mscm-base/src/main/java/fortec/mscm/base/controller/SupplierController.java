
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
import fortec.mscm.base.dto.SupplierDTO;
import fortec.mscm.base.entity.Supplier;
import fortec.mscm.base.request.SupplierQueryRequest;
import fortec.mscm.base.service.SupplierService;
import fortec.mscm.base.vo.SupplierVO;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
public class SupplierController extends CrudController<Supplier, String, SupplierService> implements ImAndExAbleController<SupplierQueryRequest> {

    @GetMapping("/page")
    public PageResult page(SupplierQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * 获取当前供应商
     * @param request
     * @return
     */
    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(SupplierQueryRequest request) {
        IPage page = service.pageForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(SupplierQueryRequest request) {
        List<Supplier> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    /**
     * 获取供应商，关键字搜索
     * @param request
     * @param keywords
     * @return
     */
    @GetMapping("/page_by_keywords")
    public PageResult pageByKeywords(SupplierQueryRequest request, @RequestParam(value = "keywords",required = false) String keywords) {
        IPage page = service.pageByKeywords(request,keywords);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * 获取当前登录供应商信息
     */
    @GetMapping("/get_current_supplier")
    public CommonResult getCurrentSupplier(){
        fortec.mscm.base.feign.vo.SupplierVO supplier = UserUtils.getUser().getSupplier();
        return CommonResult.ok("查询成功",supplier);
    }

    @Override
    public void excelExport(SupplierQueryRequest request)  {
        String fileName = "供应商信息" + DateUtils.format(DateUtils.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<Supplier> list = this.service.list(request);

        try {
            (new ExportExcel("供应商信息", SupplierVO.class)).setDataList(list).write(this.response(), fileName).dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommonResult<ImportResult> excelImport(MultipartFile file) {
        return CommonResult.ok("导入成功",this.service.excelImport(file));
    }

    @Override
    public void excelTemplate(SupplierQueryRequest request) {
        try {
            String fileName = "供应商导入模板.xlsx";
            new ExportExcel("供应商信息", SupplierDTO.class, 2).setDataList(service.list(request)).write(response(), fileName).dispose();
        } catch (Exception e) {
            throw new BusinessException("导出模板失败", e);
        }
    }

}
    