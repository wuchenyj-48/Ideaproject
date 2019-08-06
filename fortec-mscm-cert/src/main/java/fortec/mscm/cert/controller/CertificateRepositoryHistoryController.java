
package fortec.mscm.cert.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;

import fortec.mscm.cert.entity.CertificateRepositoryHistory;
import fortec.mscm.cert.request.CertificateRepositoryHistoryQueryRequest;
import fortec.mscm.cert.service.CertificateRepositoryHistoryService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
public class CertificateRepositoryHistoryController extends BaseController {

    private CertificateRepositoryHistoryService certificateRepositoryHistoryService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid CertificateRepositoryHistory entity) {
        boolean bSave = certificateRepositoryHistoryService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid CertificateRepositoryHistory entity) {
        boolean bUpdate = certificateRepositoryHistoryService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(CertificateRepositoryHistoryQueryRequest request) {
        IPage page = certificateRepositoryHistoryService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(CertificateRepositoryHistoryQueryRequest request) {
        List<CertificateRepositoryHistory> list = certificateRepositoryHistoryService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = certificateRepositoryHistoryService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

}
