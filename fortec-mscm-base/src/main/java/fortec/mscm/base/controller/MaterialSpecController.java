
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.base.entity.MaterialSpec;
import fortec.mscm.base.request.MaterialSpecQueryRequest;
import fortec.mscm.base.service.MaterialSpecService;
import fortec.mscm.base.vo.MaterialSpecVO;
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
public class MaterialSpecController extends CrudController<MaterialSpec, String, MaterialSpecService> implements ImAndExAbleController<MaterialSpecQueryRequest> {

    @GetMapping("/page")
    public PageResult page(MaterialSpecQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(MaterialSpecQueryRequest request) {
        List<MaterialSpecVO> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @PutMapping("/batch_save")
    public CommonResult batchSave(@RequestBody @Valid MaterialSpec[] children) {
        if (children == null || children.length == 0) {
            return CommonResult.error("保存失败");
        }
        boolean bSuccess = service.saveOrUpdateBatch(Lists.newArrayList(children));
        return bSuccess ? CommonResult.ok("保存成功") : CommonResult.error("保存失败");
    }

}
    