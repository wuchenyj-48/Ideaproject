
package fortec.mscm.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.base.entity.HospitalMaterial;
import fortec.mscm.base.request.HospitalMaterialQueryRequest;
import fortec.mscm.base.service.HospitalMaterialService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
public class HospitalMaterialController extends CrudController<HospitalMaterial, String, HospitalMaterialService> implements ImAndExAbleController<HospitalMaterialQueryRequest> {

    @GetMapping("/page")
    public PageResult page(HospitalMaterialQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/page_for_supplier")
    public PageResult pageForSupplier(HospitalMaterialQueryRequest request) {
        IPage page = service.pageForSupplier(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(HospitalMaterialQueryRequest request) {
        List<HospitalMaterial> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    /**
     * 医院商品启用
     * @param id
     * @return
     */
    @PostMapping("/active/{id}")
    public CommonResult active(@PathVariable("id") String id){
        service.active(id);
        return CommonResult.ok("启用成功");
    }

    /**
     * 医院商品停用
     * @param id
     * @return
     */
    @PostMapping("/deactive/{id}")
    public CommonResult deactive(@PathVariable("id") String id){
        service.deactive(id);
        return CommonResult.ok("停用成功");
    }

    /**
     * 采购订单-订单明细，关键字搜索医院商品
     * @param request
     * @return
     */
    @GetMapping("/page_by_keywords")
    public CommonResult pageByKeywords(HospitalMaterialQueryRequest request) {
        IPage page = service.pageByKeyword(request);
        return PageResult.ok("查询成功",page.getRecords(),page.getTotal());
    }

}
