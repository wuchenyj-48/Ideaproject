
package fortec.mscm.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.order.entity.DeliveryItemSn;
import fortec.mscm.order.request.DeliveryItemSnQueryRequest;
import fortec.mscm.order.service.DeliveryItemSnService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* sn生成和查询 controller
*
* @author Yangjianye
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/delivery_item_sns")
public class DeliveryItemSnController extends BaseController {

    private final DeliveryItemSnService deliveryItemSnService;

    @PostMapping("/{id}")
    public CommonResult add(@PathVariable("id") String deliveryId) {
        boolean bSave = deliveryItemSnService.saveDeliveryItemSns(deliveryId);
        return bSave ? CommonResult.ok("新增成功") : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid DeliveryItemSn entity) {
        boolean bUpdate = deliveryItemSnService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(DeliveryItemSnQueryRequest request) {
        IPage page = deliveryItemSnService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(DeliveryItemSnQueryRequest request) {
        List<DeliveryItemSn> list = deliveryItemSnService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean bRemove = deliveryItemSnService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
