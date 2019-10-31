
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.Manufacturer;
import fortec.mscm.base.request.ManufacturerQueryRequest;
import fortec.mscm.base.service.ManufacturerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 厂商 controller
 *
 * @author chenchen
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController extends BaseController {

    private ManufacturerService manufacturerService;


    @PostMapping
    public CommonResult add(@RequestBody @Valid Manufacturer entity) {
        boolean bSave = manufacturerService.add(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("厂商名称或社会信用代码不可重复");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Manufacturer entity) {
        boolean bUpdate = manufacturerService.update(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("厂商名称或社会信用代码不可重复");
    }

    @GetMapping("/page")
    public PageResult page(ManufacturerQueryRequest request) {
        IPage page = manufacturerService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(ManufacturerQueryRequest request) {
        List<Manufacturer> list = manufacturerService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = manufacturerService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid Manufacturer[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        boolean bSuccess = manufacturerService.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }

    /**
     * 根据供应商id获取厂商，关键字搜索
     * @param request
     * @param keywords
     * @return
     */
    @GetMapping("/page_by_keywords")
    public PageResult pageByKeywords(ManufacturerQueryRequest request, @RequestParam(value = "keywords", required = false) String keywords) {
        IPage page = manufacturerService.pageByKeywords(request,keywords);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

}
    