
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.Hospital;
import fortec.mscm.base.request.HospitalQueryRequest;
import fortec.mscm.base.service.HospitalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 医院 controller
 *
 * @author yuntao.zhou
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/hospitals")
public class HospitalController extends BaseController {

    private HospitalService hospitalService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid Hospital entity) {
        boolean bSave = hospitalService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Hospital entity) {
        boolean bUpdate = hospitalService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(HospitalQueryRequest request) {
        IPage<Hospital> page = hospitalService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * 当前医院信息页
     * @param request
     * @return
     */
    @GetMapping("/page_for_hospital")
    public PageResult pageForHospital(HospitalQueryRequest request) {
        IPage page = hospitalService.pageForHospital(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(HospitalQueryRequest request) {
        List<Hospital> list = hospitalService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = hospitalService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    /**
     * 关键字搜索医院信息
     * @param request
     * @param keywords
     * @return
     */
    @GetMapping("/page_by_keywords")
    public CommonResult pageByKeywords(HospitalQueryRequest request, @RequestParam(value = "keywords", required = false) String keywords) {
        IPage page = hospitalService.pageByKeywords(request,keywords);
        return PageResult.ok("查询成功",page.getRecords(),page.getTotal());
    }

}
    