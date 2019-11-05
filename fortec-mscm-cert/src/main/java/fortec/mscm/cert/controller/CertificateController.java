
package fortec.mscm.cert.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.cert.entity.Certificate;
import fortec.mscm.cert.request.CertificateQueryRequest;
import fortec.mscm.cert.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* 预定义资质 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/certificates")
public class CertificateController extends CrudController<Certificate, String, CertificateService> implements ImAndExAbleController<CertificateQueryRequest> {

    @GetMapping("/page")
    public PageResult page(CertificateQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(CertificateQueryRequest request) {
        List<Certificate> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    @GetMapping("/page_by_keywords")
    public CommonResult pageByKeywords(CertificateQueryRequest request){
        IPage<Certificate> page = service.pageByKeywords(request);
        return PageResult.ok("", page.getRecords(),page.getTotal());
    }

}
