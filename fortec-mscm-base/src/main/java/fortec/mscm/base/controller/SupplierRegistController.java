
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.base.entity.SupplierRegist;
import fortec.mscm.base.request.SupplierRegistCancelRequest;
import fortec.mscm.base.request.SupplierRegistQueryRequest;
import fortec.mscm.base.service.SupplierRegistService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * controller
 *
 * @author chenchen
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/supplier_regists")
public class SupplierRegistController extends CrudController<SupplierRegist, String, SupplierRegistService> implements ImAndExAbleController<SupplierRegistQueryRequest> {

    @GetMapping("/page")
    public PageResult page(SupplierRegistQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(SupplierRegistQueryRequest request) {
        List<SupplierRegist> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    /**
     * 供应商注册
     * @param entity
     * @return
     */
    @PostMapping("/regist")
    public CommonResult regist(@RequestBody @Valid SupplierRegist entity) {
        boolean valid = service.checkPhoneValid(entity.getApplicantMobile());
        if(!valid){
            return CommonResult.error("注册失败，手机号已注册");
        }
        boolean bSave = service.regist(entity);
        return bSave ? CommonResult.ok("注册成功", entity) : CommonResult.error("注册失败");
    }

    /**
     * 供应商审核通过
     * @param id
     * @return
     */
    @PostMapping("/pass/{id}")
    public CommonResult pass(@PathVariable("id") String id) {
        service.pass(id);
        return CommonResult.ok("审核通过");
    }

    /**
     * 供应商审核取消
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/cancel/{id}")
    public CommonResult cancel(@PathVariable("id") String id, @RequestBody SupplierRegistCancelRequest request) {
        service.cancel(id, request.getReason());
        return CommonResult.ok("取消成功");
    }

}
    