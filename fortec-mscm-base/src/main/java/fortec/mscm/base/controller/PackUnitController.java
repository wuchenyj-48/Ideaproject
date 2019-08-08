
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.common.core.utils.StringUtils;

import fortec.mscm.base.entity.PackUnit;
import fortec.mscm.base.request.PackUnitQueryRequest;
import fortec.mscm.base.service.PackUnitService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 包装单位 controller
*
* @author yuntao.zhou
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/pack_units")
public class PackUnitController extends BaseController {

    private PackUnitService packUnitService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid PackUnit entity) {
        boolean bSave = packUnitService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid PackUnit entity) {
        boolean bUpdate = packUnitService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(PackUnitQueryRequest request) {
        IPage page = packUnitService.page(request.getPage(), Wrappers.<PackUnit>query()
                    .like(StringUtils.isNotBlank(request.getId()), "id", request.getId())
                    .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                     .orderByDesc("gmt_modified")
                );

        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(PackUnitQueryRequest request) {
        List<PackUnit> list = packUnitService.list(Wrappers.<PackUnit>query().orderByDesc("gmt_modified"));
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = packUnitService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
    