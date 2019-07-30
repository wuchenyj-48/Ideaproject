
package fortec.mscm.cert.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.cert.entity.Certificate;
import fortec.mscm.cert.request.CertificateQueryRequest;
import fortec.mscm.cert.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
public class CertificateController extends BaseController {

    private CertificateService certificateService;

    @PostMapping
    public CommonResult add(@RequestBody @Valid Certificate entity) {
        boolean bSave = certificateService.saveCascadeById(entity);
        return bSave ? CommonResult.ok("新增成功", entity) : CommonResult.error("新增失败");
    }

    @PutMapping
    public CommonResult update(@RequestBody @Valid Certificate entity) {
        boolean bUpdate = certificateService.updateCascadeById(entity);
        return bUpdate ? CommonResult.ok("保存成功", entity) : CommonResult.error("保存失败");
    }

    @GetMapping("/page")
    public PageResult page(CertificateQueryRequest request) {
        IPage page = certificateService.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(CertificateQueryRequest request) {
        List<Certificate> list = certificateService.list(request);
        return CommonResult.ok("查询成功", list);
    }


    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        boolean bRemove = certificateService.removeCascadeById(id);
        return bRemove ? CommonResult.ok("删除成功") : CommonResult.error("删除失败");
    }

    @GetMapping("/page_by_keywords")
    public CommonResult pageByKeywords(CertificateQueryRequest request){
        IPage<Certificate> page = certificateService.pageByKeywords(request);
        return PageResult.ok("", page.getRecords(),page.getTotal());
    }

}
