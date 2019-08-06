
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.consts.CommonConsts;
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
        boolean bSave = manufacturerService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Manufacturer entity) {
        boolean bUpdate = manufacturerService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(ManufacturerQueryRequest request) {
        request.setSupplierId(CommonConsts.SUPPLIER_ID);
        IPage page = manufacturerService.page(request.getPage(), Wrappers.<Manufacturer>query()
                .eq(request.getSupplierId() != null, "supplier_id", request.getSupplierId())
                .like(request.getCompanyCode() != null, "company_code", request.getCompanyCode())
                .like(request.getName() != null, "name", request.getName())
                .orderByDesc("gmt_modified")
        );

        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(ManufacturerQueryRequest request) {
        List<Manufacturer> list = manufacturerService.list(Wrappers.<Manufacturer>query()
                .eq(request.getSupplierId() != null, "supplier_id", request.getSupplierId())
                .orderByDesc("gmt_modified")
        );
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

    @GetMapping("/page_by_keywords")
    public PageResult page(ManufacturerQueryRequest request, @RequestParam(value = "keywords", required = false) String keywords) {
        IPage page = manufacturerService.page(request.getPage(), Wrappers.<Manufacturer>query()
                .eq(StringUtils.isNotBlank(request.getSupplierId()), "supplier_id", request.getSupplierId())
                .like(StringUtils.isNotBlank(keywords), "company_code", keywords)
                .like(StringUtils.isNotBlank(keywords), "name", keywords)
                .orderByDesc("gmt_modified")
        );

        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

}
    