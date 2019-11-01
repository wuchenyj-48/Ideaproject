
package fortec.mscm.settlement.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
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
public class InvoiceController extends CrudController<Invoice, String, InvoiceService> implements ImAndExAbleController<InvoiceQueryRequest> {

    @PostMapping
    public CommonResult add(@RequestBody @Valid Invoice entity) {
        boolean bSave = service.add(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @GetMapping("/page")
    public PageResult page(InvoiceQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(InvoiceQueryRequest request) {
        List<Invoice> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @PostMapping("/audit/{id}")
    public CommonResult audit(@PathVariable("id") String id){
        service.audit(id);
        return CommonResult.ok("审核成功");
    }

}
    