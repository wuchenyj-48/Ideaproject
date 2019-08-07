package fortec.mscm.base.feign.api;

import fortec.mscm.base.feign.vo.HospitalVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 供应商Feign API接口
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/7 9:10
 * @Version: 1.0
 */
public interface HospitalFeignApi {

    /**
     * 通过机构ID 获取供应商信息
     * @param officeId
     * @return
     */
    @GetMapping("/feign/hospitals/find_by_office_id")
    HospitalVO findByOfficeId(@RequestParam("officeId") String officeId);

}
