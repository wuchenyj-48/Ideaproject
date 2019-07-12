
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.HospitalLocation;
import fortec.mscm.base.request.HospitalLocationQueryRequest;
import fortec.mscm.base.service.HospitalLocationService;
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
@RequestMapping("/hospital_locations")
public class HospitalLocationController extends BaseController {

    private HospitalLocationService hospitalLocationService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid HospitalLocation entity) {
        boolean bSave = hospitalLocationService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid HospitalLocation entity) {
        boolean bUpdate = hospitalLocationService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(HospitalLocationQueryRequest request) {
        IPage page = hospitalLocationService.page(request);

        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(HospitalLocationQueryRequest request) {
        List<HospitalLocation> list = hospitalLocationService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = hospitalLocationService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid HospitalLocation[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        boolean bSuccess = hospitalLocationService.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }

}
    