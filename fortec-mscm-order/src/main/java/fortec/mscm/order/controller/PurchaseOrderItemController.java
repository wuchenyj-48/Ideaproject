
package fortec.mscm.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
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
public class PurchaseOrderItemController extends CrudController<PurchaseOrderItem, String, PurchaseOrderItemService> implements ImAndExAbleController<PurchaseOrderItemQueryRequest> {

    @PostMapping
    public CommonResult add(@RequestBody @Valid PurchaseOrderItem entity) {
        service.add(entity);
        return CommonResult.ok("新增成功", entity);
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid PurchaseOrderItem entity) {
        service.update(entity);
        return CommonResult.ok("保存成功", entity);
    }

    @GetMapping("/page")
    public PageResult page(PurchaseOrderItemQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(PurchaseOrderItemQueryRequest request) {
        List<PurchaseOrderItem> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        service.delete(id);
        return CommonResult.ok("删除成功");
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid PurchaseOrderItem[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        service.batchSave(children);
        return CommonResult.ok("保存成功");
    }
}
