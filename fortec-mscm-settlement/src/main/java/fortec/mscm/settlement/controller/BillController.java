
package fortec.mscm.settlement.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.settlement.entity.Bill;
import fortec.mscm.settlement.request.BillQueryRequest;
import fortec.mscm.settlement.service.BillService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 记账单 controller
*
* @author HL
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/bills")
public class BillController extends CrudController<Bill, String, BillService> implements ImAndExAbleController<BillQueryRequest> {

    @PostMapping
    public CommonResult add(@RequestBody @Valid Bill entity) {
        boolean bSave = service.add(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Bill entity) {
        boolean bUpdate = service.add(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(BillQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(BillQueryRequest request) {
        IPage page = service.pageForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(BillQueryRequest request) {
        List<Bill> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }


}
    