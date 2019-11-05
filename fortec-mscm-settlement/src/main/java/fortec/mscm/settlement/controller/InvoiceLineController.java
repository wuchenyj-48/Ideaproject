
package fortec.mscm.settlement.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.settlement.entity.InvoiceLine;
import fortec.mscm.settlement.request.InvoiceLineQueryRequest;
import fortec.mscm.settlement.service.InvoiceLineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 发票单行信息 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/invoice_lines")
public class InvoiceLineController extends CrudController<InvoiceLine, String, InvoiceLineService> implements ImAndExAbleController<InvoiceLineQueryRequest> {

    @GetMapping("/page")
    public PageResult page(InvoiceLineQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(InvoiceLineQueryRequest request) {
        List<InvoiceLine> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid InvoiceLine[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        boolean bSuccess = service.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }
}
