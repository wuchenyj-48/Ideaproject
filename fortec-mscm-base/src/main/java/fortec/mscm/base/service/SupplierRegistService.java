
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
     * 检查申请人手机号是否可用，
     * @param phone
     * @return 如果可用，返回{@code true}，否则返回 {@code false}
     */
    boolean checkPhoneValid(String phone);

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
    