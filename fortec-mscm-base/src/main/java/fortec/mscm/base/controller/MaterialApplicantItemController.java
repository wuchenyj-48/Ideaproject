
package fortec.mscm.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
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
public class MaterialApplicantItemController extends CrudController<MaterialApplicantItem, String, MaterialApplicantItemService> implements ImAndExAbleController<MaterialApplicantItemQueryRequest> {

    @GetMapping("/page")
    public PageResult page(MaterialApplicantItemQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialApplicantItemQueryRequest request) {
        List<MaterialApplicantItem> list = service.list(request);
        return CommonResult.ok("查询成功", list);
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
        boolean bSuccess = service.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }
}
