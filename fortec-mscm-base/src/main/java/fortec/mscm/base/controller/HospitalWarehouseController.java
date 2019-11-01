
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.base.entity.HospitalWarehouse;
import fortec.mscm.base.request.HospitalWarehouseQueryRequest;
import fortec.mscm.base.service.HospitalWarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 医院收货地点 controller
 *
 * @author yuntao.zhou
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/hospital_warehouses")
public class HospitalWarehouseController extends CrudController<HospitalWarehouse, String, HospitalWarehouseService> implements ImAndExAbleController<HospitalWarehouseQueryRequest> {

    @GetMapping("/page")
    public PageResult page(HospitalWarehouseQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(HospitalWarehouseQueryRequest request) {
        List<HospitalWarehouse> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid HospitalWarehouse[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        boolean bSuccess = service.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }

}
    