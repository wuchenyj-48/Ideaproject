
package fortec.mscm.order.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

import fortec.mscm.order.entity.Delivery;
import fortec.mscm.order.request.DeliveryQueryRequest;
import fortec.mscm.order.service.DeliveryService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 发货单 controller
 *
 * @author Yangjy
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/deliverys")
public class DeliveryController extends BaseController {

    private final DeliveryService deliveryService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid Delivery entity) {
        boolean bSave = deliveryService.saveDeliverys(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Delivery entity) {
        boolean bUpdate = deliveryService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(DeliveryQueryRequest request) {
        IPage page = deliveryService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(DeliveryQueryRequest request) {
        List<Delivery> list = deliveryService.list(request);
        return CommonResult.ok("查询成功", list);
    }
    /**
     * @param id
     * @Description: 发货
     * @return: fortec.common.core.model.CommonResult
     */
    @PostMapping("/{id}/delivery")
    public CommonResult delivery(@PathVariable("id") String id) {

        boolean deliverStatusBool = deliveryService.updateDeliverStatus(id);
        return deliverStatusBool ? CommonResult.ok("发货成功") : CommonResult.error("发货失败");
    }

    /**
     * @param id
     * @Description: 取消发货
     * @return: fortec.common.core.model.CommonResult
     */
    @PostMapping("/{id}/cancelDelivery")
    public CommonResult cancelDelivery(@PathVariable("id") String id) {

        boolean cancelDeliverBool = deliveryService.cancelDelivery(id);
        return cancelDeliverBool ? CommonResult.ok("取消发货成功") : CommonResult.error("取消发货失败");
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean bRemove = deliveryService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }


    @GetMapping("/sendPage")
    public PageResult sendPage(DeliveryQueryRequest request) {
        IPage page = deliveryService.sendPage(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }
    @GetMapping("/allDeliveryPage")
    public PageResult allDeliveryPage(DeliveryQueryRequest request) {
        IPage page = deliveryService.allDeliveryPage(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }
}
    