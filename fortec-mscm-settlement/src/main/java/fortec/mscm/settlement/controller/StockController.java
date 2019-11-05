
package fortec.mscm.settlement.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.settlement.entity.Stock;
import fortec.mscm.settlement.request.StockQueryRequest;
import fortec.mscm.settlement.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class StockController extends CrudController<Stock, String, StockService> implements ImAndExAbleController<StockQueryRequest> {

    @GetMapping("/page")
    public PageResult page(StockQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(StockQueryRequest request) {
        List<Stock> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(StockQueryRequest request) {
        IPage page = service.pageForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

}
