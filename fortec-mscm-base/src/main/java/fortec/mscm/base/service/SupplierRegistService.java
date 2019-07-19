
package fortec.mscm.base.service;

import fortec.mscm.base.entity.SupplierRegist;

import fortec.common.core.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;


/**
*  service 接口
*
* @author chenchen
* @version 1.0
*/
public interface SupplierRegistService extends IBaseService<SupplierRegist> {

    /**
     * 审核通过
     * @param id
     */
    @Transactional
    void pass(String id);

    /**
     * 取消审核
     * @param id
     * @param reason
     */
    @Transactional
    void cancel(String id,String reason);

}
    