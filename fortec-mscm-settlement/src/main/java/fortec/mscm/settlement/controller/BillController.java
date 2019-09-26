
package fortec.mscm.settlement.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

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
public class BillController extends BaseController {

    private final  BillService billService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid Bill entity) {
        boolean bSave = billService.add(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Bill entity) {
        boolean bUpdate = billService.add(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(BillQueryRequest request) {
        IPage page = billService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(BillQueryRequest request) {
        IPage page = billService.pageForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(BillQueryRequest request) {
        List<Bill> list = billService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean bRemove = billService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
    