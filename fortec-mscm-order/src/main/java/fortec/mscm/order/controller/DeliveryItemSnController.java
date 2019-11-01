
package fortec.mscm.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.order.entity.DeliveryItemSn;
import fortec.mscm.order.request.DeliveryItemSnQueryRequest;
import fortec.mscm.order.service.DeliveryItemSnService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
public class DeliveryItemSnController extends CrudController<DeliveryItemSn, String, DeliveryItemSnService> implements ImAndExAbleController<DeliveryItemSnQueryRequest> {

    @PostMapping("/{id}")
    public CommonResult saveDeliveryItemSns(@PathVariable("id") String deliveryId) {
        List<DeliveryItemSn> deliveryItemSnList = service.saveDeliveryItemSns(deliveryId);
        return CommonResult.ok("新增成功",deliveryItemSnList);
    }

    @GetMapping("/page")
    public PageResult page(DeliveryItemSnQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(DeliveryItemSnQueryRequest request) {
        List<DeliveryItemSn> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }


}
