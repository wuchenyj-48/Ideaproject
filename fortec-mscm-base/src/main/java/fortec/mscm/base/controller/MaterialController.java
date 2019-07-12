
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.request.MaterialQueryRequest;
import fortec.mscm.base.service.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 商品 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/materials")
public class MaterialController extends BaseController {

    private MaterialService materialService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid Material entity) {
        boolean bSave = materialService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Material entity) {
        boolean bUpdate = materialService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(MaterialQueryRequest request) {
//        IPage page = materialService.page(request.getPage(), Wrappers.<Material>query()
//                    .eq(request.getMaterialTypeCode() != null, "material_type_code", request.getMaterialTypeCode())
//                    .like(StringUtils.isNotBlank(request.getMaterialName()), "material_name", request.getMaterialName())
//                    .like(StringUtils.isNotBlank(request.getMaterialTradeName()), "material_trade_name", request.getMaterialTradeName())
//                     .orderByDesc("gmt_modified")
//                );
        IPage<Material> page = materialService.page(request);

        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialQueryRequest request) {
        List<Material> list = materialService.list(Wrappers.<Material>query().orderByDesc("gmt_modified"));
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = materialService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
    