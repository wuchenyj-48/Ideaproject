
package fortec.mscm.order.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
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
public class PurchaseOrderController extends CrudController<PurchaseOrder, String, PurchaseOrderService> implements ImAndExAbleController<PurchaseOrderQueryRequest> {

    @PostMapping
    public CommonResult add(@RequestBody @Valid PurchaseOrder entity) {
        boolean bSave = service.add(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @GetMapping("/page")
    public PageResult page(PurchaseOrderQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }


    @GetMapping("/list")
    public CommonResult list(PurchaseOrderQueryRequest request) {
        List<PurchaseOrder> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    /**
     * 医院提交采购订单
     *
     * @param id
     * @return
     */
    @PostMapping("/submit_order/{id}")
    public CommonResult submitOrder(@PathVariable("id") String id) {
        service.submitOrder(id);
        return CommonResult.ok("提交成功");
    }

    /**
     * 医院采购订单审核通过
     *
     * @param id
     * @return
     */
    @PostMapping("/pass/{id}")
    public CommonResult passOrder(@PathVariable("id") String id) {
        service.passOrder(id);
        return CommonResult.ok("通过成功");
    }

    /**
     * 供应商订单列表页
     *
     * @param request
     * @return
     */
    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(PurchaseOrderQueryRequest request) {
        IPage page = service.pageForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }


    /**
     * @param request
     * @Description: 查询未发货和部分发货订单
     * @return: fortec.common.core.model.PageResult
     * @author Yangjy
     */
    @GetMapping("/page_For_Delivery")
    public PageResult pageForDelivery(PurchaseOrderQueryRequest request) {
        IPage page = service.pageForDelivery(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * 确认可供货
     *
     * @param id
     * @return
     */
    @PostMapping("/able/{id}")
    public CommonResult able(@PathVariable("id") String id) {
        service.able(id);
        return CommonResult.ok("通过成功");
    }

    /**
     * 确认不可供货
     *
     * @param id
     * @return
     */
    @PostMapping("/disable/{id}")
    public CommonResult disable(@PathVariable("id") String id) {
        service.disable(id);
        return CommonResult.ok("通过成功");
    }

}
    