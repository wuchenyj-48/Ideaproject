
package fortec.mscm.order.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

import fortec.mscm.order.entity.PurchaseOrder;
import fortec.mscm.order.request.PurchaseOrderQueryRequest;
import fortec.mscm.order.service.PurchaseOrderService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 采购订单 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/purchase_orders")
public class PurchaseOrderController extends BaseController {

    private PurchaseOrderService purchaseOrderService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid PurchaseOrder entity) {
        boolean bSave = purchaseOrderService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid PurchaseOrder entity) {
        boolean bUpdate = purchaseOrderService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(PurchaseOrderQueryRequest request) {
        IPage page = purchaseOrderService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(PurchaseOrderQueryRequest request) {
        List<PurchaseOrder> list = purchaseOrderService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = purchaseOrderService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
    