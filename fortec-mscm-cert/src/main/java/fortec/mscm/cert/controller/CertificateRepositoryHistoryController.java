
package fortec.mscm.cert.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.cert.entity.CertificateRepositoryHistory;
import fortec.mscm.cert.request.CertificateRepositoryHistoryQueryRequest;
import fortec.mscm.cert.service.CertificateRepositoryHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* 资质历史版本 controller
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@RestController
@RequestMapping("/certificate_repository_historys")
public class CertificateRepositoryHistoryController extends CrudController<CertificateRepositoryHistory, String, CertificateRepositoryHistoryService> implements ImAndExAbleController<CertificateRepositoryHistoryQueryRequest> {

    @GetMapping("/page")
    public PageResult page(CertificateRepositoryHistoryQueryRequest request) {
        IPage page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(CertificateRepositoryHistoryQueryRequest request) {
        List<CertificateRepositoryHistory> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }
    
}
