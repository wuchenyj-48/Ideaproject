
package fortec.mscm.base.service;

import fortec.mscm.base.entity.Hospital;

import fortec.common.core.service.IBaseService;


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

}
    