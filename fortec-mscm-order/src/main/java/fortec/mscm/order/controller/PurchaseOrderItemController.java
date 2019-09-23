
package fortec.mscm.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.request.PurchaseOrderItemQueryRequest;
import fortec.mscm.order.service.PurchaseOrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 采购订单明细 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/purchase_order_items")
public class PurchaseOrderItemController extends BaseController {

    private PurchaseOrderItemService purchaseOrderItemService;

    @PostMapping
    public CommonResult add(@RequestBody PurchaseOrderItem entity) {
        purchaseOrderItemService.add(entity);
        return CommonResult.ok("新增成功", entity);
    }

    @PutMapping
    public CommonResult update(@RequestBody PurchaseOrderItem entity) {
        purchaseOrderItemService.add(entity);
        return CommonResult.ok("保存成功", entity);
    }

    @GetMapping("/page")
    public PageResult page(PurchaseOrderItemQueryRequest request) {
        IPage page = purchaseOrderItemService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(PurchaseOrderItemQueryRequest request) {
        List<PurchaseOrderItem> list = purchaseOrderItemService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        purchaseOrderItemService.delete(id);
        return CommonResult.ok("删除成功");
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid PurchaseOrderItem[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        purchaseOrderItemService.batchSave(children);
        return CommonResult.ok("保存成功");
    }
}
