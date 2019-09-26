
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
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
public class HospitalWarehouseController extends BaseController {

    private HospitalWarehouseService hospitalWarehouseService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid HospitalWarehouse entity) {
        boolean bSave = hospitalWarehouseService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid HospitalWarehouse entity) {
        boolean bUpdate = hospitalWarehouseService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(HospitalWarehouseQueryRequest request) {
        IPage page = hospitalWarehouseService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(HospitalWarehouseQueryRequest request) {
        List<HospitalWarehouse> list = hospitalWarehouseService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = hospitalWarehouseService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid HospitalWarehouse[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        boolean bSuccess = hospitalWarehouseService.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }

}
    