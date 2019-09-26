
package fortec.mscm.settlement.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.settlement.entity.BillItem;
import fortec.mscm.settlement.request.BillItemQueryRequest;
import fortec.mscm.settlement.service.BillItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 记账单明细 controller
*
* @author hl
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/bill_items")
public class BillItemController extends BaseController {

    private final  BillItemService billItemService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid BillItem entity) {
        billItemService.add(entity);
        return CommonResult.ok("新增成功", entity);
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid BillItem entity) {
        billItemService.add(entity);
        return CommonResult.ok("保存成功", entity);
    }

    @GetMapping("/page")
    public PageResult page(BillItemQueryRequest request) {
        IPage page = billItemService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(BillItemQueryRequest request) {
        List<BillItem> list = billItemService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        billItemService.delete(id);
        return CommonResult.ok("删除成功");
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid BillItem[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        billItemService.batchSave(children);
        return CommonResult.ok("保存成功");
    }
}
