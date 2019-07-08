
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.common.core.utils.StringUtils;

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
        IPage page = hospitalService.page(request.getPage(), Wrappers.<Hospital>query()
                    .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                    .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                     .orderByDesc("gmt_modified")
                );

        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(HospitalQueryRequest request) {
        List<Hospital> list = hospitalService.list(Wrappers.<Hospital>query().orderByDesc("gmt_modified"));
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = hospitalService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
    