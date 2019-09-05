
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.IBaseService;
import fortec.mscm.base.entity.Hospital;
import fortec.mscm.base.request.HospitalQueryRequest;

import java.util.List;


/**
* 医院 service 接口
*
* @author yuntao.zhou
* @version 1.0
*/
public interface HospitalService extends IBaseService<Hospital> {

    /**
     * 通过组织机构查询医院信息
     * @param officeId
     * @return
     */
    Hospital findByOfficeId(String officeId);

    /**
     * page页
     * @param request
     * @return
     */
    IPage<Hospital> page(HospitalQueryRequest request);

    /**
     * 当前医院信息页
     * @param request
     * @return
     */
    IPage<Hospital> pageForHospital(HospitalQueryRequest request);

    List<Hospital> list(HospitalQueryRequest request);

    /**
     * 关键字搜索医院信息
     * @param request
     * @param keywords
     * @return
     */
    IPage<Hospital> pageByKeywords(HospitalQueryRequest request,String keywords);

}
    