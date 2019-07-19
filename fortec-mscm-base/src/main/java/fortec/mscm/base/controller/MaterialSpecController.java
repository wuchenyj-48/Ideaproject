
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.common.core.utils.StringUtils;

import fortec.mscm.base.entity.MaterialSpec;
import fortec.mscm.base.request.MaterialSpecQueryRequest;
import fortec.mscm.base.service.MaterialSpecService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 商品规格 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/material_specs")
public class MaterialSpecController extends BaseController {

    private MaterialSpecService materialSpecService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid MaterialSpec entity) {
        boolean bSave = materialSpecService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid MaterialSpec entity) {
        boolean bUpdate = materialSpecService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(MaterialSpecQueryRequest request) {
        IPage page = materialSpecService.page(request.getPage(), Wrappers.<MaterialSpec>query()
                    .like(StringUtils.isNotBlank(request.getMaterialSpec()), "material_spec", request.getMaterialSpec())
                    .between(request.getBeginPrice() != null && request.getEndPrice() != null, "price", request.getBeginPrice(), request.getEndPrice())
                     .orderByDesc("gmt_modified")
                );

        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialSpecQueryRequest request) {
        List<MaterialSpec> list = materialSpecService.list(Wrappers.<MaterialSpec>query()
                .like(StringUtils.isNotBlank(request.getMaterialSpec()), "material_spec", request.getMaterialSpec())
                .between(request.getBeginPrice() != null && request.getEndPrice() != null, "price", request.getBeginPrice(), request.getEndPrice())
                .orderByDesc("gmt_modified")
        );
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = materialSpecService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid MaterialSpec[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        boolean bSuccess = materialSpecService.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }

    @GetMapping("/page_by_keywords")
    public PageResult pageByKeywords(MaterialSpecQueryRequest request) {
        IPage<MaterialSpec> page = materialSpecService.pageByKeywords(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

}
    