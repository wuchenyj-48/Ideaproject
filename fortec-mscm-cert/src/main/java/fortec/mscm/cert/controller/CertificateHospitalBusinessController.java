
package fortec.mscm.cert.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.cert.entity.CertificateHospitalBusiness;
import fortec.mscm.cert.request.CertificateHospitalBusinessQueryRequest;
import fortec.mscm.cert.service.CertificateHospitalBusinessService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class CertificateHospitalBusinessController extends CrudController<CertificateHospitalBusiness, String, CertificateHospitalBusinessService> implements ImAndExAbleController<CertificateHospitalBusinessQueryRequest> {

    @GetMapping("/page")
    public PageResult page(CertificateHospitalBusinessQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(CertificateHospitalBusinessQueryRequest request) {
        List<CertificateHospitalBusiness> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }
    
}
