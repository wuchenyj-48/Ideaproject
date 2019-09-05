
package fortec.mscm.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

import fortec.mscm.base.entity.HospitalMaterial;
import fortec.mscm.base.request.HospitalMaterialQueryRequest;
import fortec.mscm.base.service.HospitalMaterialService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* HospitalMaterial controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/hospital_materials")
public class HospitalMaterialController extends BaseController {

    private HospitalMaterialService hospitalMaterialService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid HospitalMaterial entity) {
        boolean bSave = hospitalMaterialService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid HospitalMaterial entity) {
        boolean bUpdate = hospitalMaterialService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(HospitalMaterialQueryRequest request) {
        IPage page = hospitalMaterialService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(HospitalMaterialQueryRequest request) {
        List<HospitalMaterial> list = hospitalMaterialService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = hospitalMaterialService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    /**
     * 医院商品启用
     * @param id
     * @return
     */
    @PostMapping("/active/{id}")
    public CommonResult active(@PathVariable("id") String id){
        hospitalMaterialService.active(id);
        return CommonResult.ok("启用成功");
    }

    /**
     * 医院商品停用
     * @param id
     * @return
     */
    @PostMapping("/deactive/{id}")
    public CommonResult deactive(@PathVariable("id") String id){
        hospitalMaterialService.deactive(id);
        return CommonResult.ok("停用成功");
    }

}
