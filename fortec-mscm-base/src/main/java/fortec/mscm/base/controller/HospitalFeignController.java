package fortec.mscm.base.controller;

import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.Hospital;
import fortec.mscm.base.feign.api.HospitalFeignApi;
import fortec.mscm.base.feign.vo.HospitalVO;
import fortec.mscm.base.feign.vo.SupplierVO;
import fortec.mscm.base.service.HospitalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 医院 feign 接口实现
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/7 9:49
 * @Version: 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class HospitalFeignController extends BaseController implements HospitalFeignApi {

    private final HospitalService hospitalService;

    @Override
    public HospitalVO findByOfficeId(String officeId) {
        Hospital hospital = hospitalService.findByOfficeId(officeId);
        if(hospital == null){
            return null;
        }
        HospitalVO vo = new HospitalVO();
        BeanUtils.copyProperties(hospital, vo);

        return vo;
    }
}
