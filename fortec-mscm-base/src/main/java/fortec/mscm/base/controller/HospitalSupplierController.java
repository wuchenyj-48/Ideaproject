
package fortec.mscm.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.HospitalSupplier;
import fortec.mscm.base.request.HospitalSupplierQueryRequest;
import fortec.mscm.base.service.HospitalSupplierService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* HospitalSupplier controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/hospital_suppliers")
public class HospitalSupplierController extends BaseController {

    private HospitalSupplierService hospitalSupplierService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid HospitalSupplier entity) {
        boolean bSave = hospitalSupplierService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid HospitalSupplier entity) {
        boolean bUpdate = hospitalSupplierService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(HospitalSupplierQueryRequest request) {
        IPage page = hospitalSupplierService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(HospitalSupplierQueryRequest request) {
        List<HospitalSupplier> list = hospitalSupplierService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = hospitalSupplierService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    /**
     * 根据供应商id获取医院，关键字搜索
     * @param request
     * @return
     */
    @GetMapping("/page_by_keywords")
    public CommonResult pageByKeywords(HospitalSupplierQueryRequest request){
        IPage<HospitalSupplier> page = hospitalSupplierService.pageByKeywords(request);
        return PageResult.ok("查询成功", page.getRecords(),page.getTotal());
    }

    /**
     * 医院启用供应商，状态设为正常
     * @param id
     * @return
     */
    @PostMapping("enable/{id}")
    public CommonResult enable(@PathVariable("id") String id){
        hospitalSupplierService.enable(id);
        return CommonResult.ok("启用成功");
    }

    /**
     * 医院停用供应商，状态设为停用
     * @param id
     * @return
     */
    @PostMapping("disable/{id}")
    public CommonResult disable(@PathVariable("id") String id){
        hospitalSupplierService.disable(id);
        return CommonResult.ok("停用成功");
    }

    /**
     * 医院的供应商关系界面
     * @param request
     * @return
     */
    @GetMapping("/page_for_hospital")
    public PageResult pageForHospital(HospitalSupplierQueryRequest request) {
        IPage page = hospitalSupplierService.pageForHospital(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * 供应商的医院关系界面
     * @param request
     * @return
     */
    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(HospitalSupplierQueryRequest request) {
        IPage page = hospitalSupplierService.pageForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

}
