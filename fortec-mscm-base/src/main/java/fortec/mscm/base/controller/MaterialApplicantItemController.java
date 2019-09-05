
package fortec.mscm.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.MaterialApplicantItem;
import fortec.mscm.base.request.MaterialApplicantItemQueryRequest;
import fortec.mscm.base.service.MaterialApplicantItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* MaterialApplicantItem controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/material_applicant_items")
public class MaterialApplicantItemController extends BaseController {

    private MaterialApplicantItemService materialApplicantItemService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid MaterialApplicantItem entity) {
        boolean bSave = materialApplicantItemService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid MaterialApplicantItem entity) {
        boolean bUpdate = materialApplicantItemService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(MaterialApplicantItemQueryRequest request) {
        IPage page = materialApplicantItemService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialApplicantItemQueryRequest request) {
        List<MaterialApplicantItem> list = materialApplicantItemService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = materialApplicantItemService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    /**
     * 批量保存供货明细
     * @param children
     * @return
     */
    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid MaterialApplicantItem[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        boolean bSuccess = materialApplicantItemService.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }
}
