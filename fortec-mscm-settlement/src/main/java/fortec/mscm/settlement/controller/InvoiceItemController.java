
package fortec.mscm.settlement.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

import fortec.mscm.settlement.DTO.BatchDeleteDTO;
import fortec.mscm.settlement.DTO.InvoiceItemDTO;
import fortec.mscm.settlement.entity.InvoiceItem;
import fortec.mscm.settlement.request.InvoiceItemQueryRequest;
import fortec.mscm.settlement.service.InvoiceItemService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 发票单明细 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/invoice_items")
public class InvoiceItemController extends BaseController {

    private final  InvoiceItemService invoiceItemService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid InvoiceItem entity) {
        boolean bSave = invoiceItemService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid InvoiceItem entity) {
        boolean bUpdate = invoiceItemService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(InvoiceItemDTO invoiceItemDTO) {
        IPage page = invoiceItemService.page(invoiceItemDTO);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(InvoiceItemQueryRequest request) {
        List<InvoiceItem> list = invoiceItemService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean bRemove = invoiceItemService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid InvoiceItem[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("关联失败");
        }
        boolean bSuccess = invoiceItemService.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("关联成功") : CommonResult.error("关联失败");
    }

    @GetMapping("/page_for_relate")
    public PageResult pageForRelate(InvoiceItemDTO invoiceItemDTO) {
        IPage page = invoiceItemService.pageForRelate(invoiceItemDTO);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/page_for_view")
    public PageResult pageForView(InvoiceItemDTO invoiceItemDTO) {
        IPage page = invoiceItemService.pageForView(invoiceItemDTO);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @PostMapping("/batch_delete")
    public CommonResult batchDelete(@RequestBody BatchDeleteDTO batchDeleteDTO) {
        if (batchDeleteDTO.getIds() == null || batchDeleteDTO.getIds().length==0){
            return CommonResult.error("删除失败");
        }
        invoiceItemService.batchDelete(batchDeleteDTO.getIds(),batchDeleteDTO.getInvoiceLineId());
        return CommonResult.ok("删除成功");
    }

}
