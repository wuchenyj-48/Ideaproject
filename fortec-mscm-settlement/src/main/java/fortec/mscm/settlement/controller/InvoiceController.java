
package fortec.mscm.settlement.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

import fortec.mscm.settlement.entity.Invoice;
import fortec.mscm.settlement.request.InvoiceQueryRequest;
import fortec.mscm.settlement.service.InvoiceService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 开票单 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/invoices")
public class InvoiceController extends BaseController {

    private final  InvoiceService invoiceService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid Invoice entity) {
        boolean bSave = invoiceService.add(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Invoice entity) {
        boolean bUpdate = invoiceService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(InvoiceQueryRequest request) {
        IPage page = invoiceService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(InvoiceQueryRequest request) {
        List<Invoice> list = invoiceService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean bRemove = invoiceService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
    