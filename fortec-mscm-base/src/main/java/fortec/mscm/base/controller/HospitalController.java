
package fortec.mscm.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.model.CommonResult;
import fortec.common.core.model.PageResult;
import fortec.common.core.mvc.controller.CrudController;
import fortec.common.core.mvc.controller.ImAndExAbleController;
import fortec.mscm.base.entity.Hospital;
import fortec.mscm.base.feign.vo.HospitalVO;
import fortec.mscm.base.request.HospitalQueryRequest;
import fortec.mscm.base.service.HospitalService;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 医院 controller
 *
 * @author yuntao.zhou
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/hospitals")
public class HospitalController extends CrudController<Hospital, String, HospitalService> implements ImAndExAbleController<HospitalQueryRequest> {

    @GetMapping("/page")
    public PageResult page(HospitalQueryRequest request) {
        IPage<Hospital> page = service.page(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * 当前医院信息页
     * @param request
     * @return
     */
    @GetMapping("/page_for_hospital")
    public PageResult pageForHospital(HospitalQueryRequest request) {
        IPage page = service.pageForHospital(request);
        return PageResult.ok("查询成功", page.getRecords(), page.getTotal());
    }

    @GetMapping("/list")
    public CommonResult list(HospitalQueryRequest request) {
        List<Hospital> list = service.list(request);
        return CommonResult.ok("查询成功", list);
    }

    /**
     * 关键字搜索医院信息
     * @param request
     * @param keywords
     * @return
     */
    @GetMapping("/page_by_keywords")
    public CommonResult pageByKeywords(HospitalQueryRequest request, @RequestParam(value = "keywords", required = false) String keywords) {
        IPage page = service.pageByKeywords(request,keywords);
        return PageResult.ok("查询成功",page.getRecords(),page.getTotal());
    }

    /**
     * 获取当前登录医院信息
     */
    @GetMapping("/get_current_hospital")
    public CommonResult getCurrentHospital(){
        HospitalVO hospital = UserUtils.getUser().getHospital();
        return CommonResult.ok("查询成功",hospital);
    }


}
    