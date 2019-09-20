
package fortec.mscm.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.order.entity.Delivery;
import fortec.mscm.order.entity.DeliveryItem;
import fortec.mscm.order.request.DeliveryItemQueryRequest;
import fortec.mscm.order.service.DeliveryItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 发货单明细 controller
*
* @author Yangjy
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/delivery_items")
public class DeliveryItemController extends BaseController {

    private final  DeliveryItemService deliveryItemService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid DeliveryItem entity) {
        boolean bSave = deliveryItemService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid DeliveryItem entity) {
        boolean bUpdate = deliveryItemService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(DeliveryItemQueryRequest request) {
        IPage page = deliveryItemService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(DeliveryItemQueryRequest request) {
        List<DeliveryItem> list = deliveryItemService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean bRemove = deliveryItemService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid DeliveryItem[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        boolean bSuccess = deliveryItemService.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }

    @GetMapping("/surplusOrder")
    public CommonResult surplusPurchaseOrder( Delivery delivery) {
        List<DeliveryItem> deliveryItemList = deliveryItemService.surplusPurchaseOrder(delivery);
        return CommonResult.ok("查询成功", deliveryItemList);
    }
}
