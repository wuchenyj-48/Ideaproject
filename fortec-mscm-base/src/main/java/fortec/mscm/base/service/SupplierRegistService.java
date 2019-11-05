
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.SupplierRegist;

import fortec.common.core.service.IBaseService;
import fortec.mscm.base.request.SupplierRegistQueryRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
     * 供应商注册
     * @param supplierRegist
     * @return
     */
    boolean regist(SupplierRegist supplierRegist);

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

    IPage<SupplierRegist> page(SupplierRegistQueryRequest request);

    List<SupplierRegist> list(SupplierRegistQueryRequest request);

}
    