
package fortec.mscm.cert.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

import fortec.mscm.cert.entity.CertificateHospitalBusiness;
import fortec.mscm.cert.request.CertificateHospitalBusinessQueryRequest;
import fortec.mscm.cert.service.CertificateHospitalBusinessService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* 院方业务资质定义 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/certificate_hospital_businesss")
public class CertificateHospitalBusinessController extends BaseController {

    private CertificateHospitalBusinessService certificateHospitalBusinessService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid CertificateHospitalBusiness entity) {
        boolean bSave = certificateHospitalBusinessService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid CertificateHospitalBusiness entity) {
        boolean bUpdate = certificateHospitalBusinessService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(CertificateHospitalBusinessQueryRequest request) {
        IPage page = certificateHospitalBusinessService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(CertificateHospitalBusinessQueryRequest request) {
        List<CertificateHospitalBusiness> list = certificateHospitalBusinessService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = certificateHospitalBusinessService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
