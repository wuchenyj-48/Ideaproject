
package fortec.mscm.settlement.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

import fortec.mscm.settlement.entity.Stock;
import fortec.mscm.settlement.request.StockQueryRequest;
import fortec.mscm.settlement.service.StockService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 库存管理 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/stocks")
public class StockController extends BaseController {

    private final StockService stockService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid Stock entity) {
        boolean bSave = stockService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Stock entity) {
        boolean bUpdate = stockService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(StockQueryRequest request) {
        IPage page = stockService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(StockQueryRequest request) {
        List<Stock> list = stockService.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(StockQueryRequest request) {
        IPage page = stockService.pageForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean bRemove = stockService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
