
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.SupplierApplicant;
import fortec.mscm.base.request.SupplierApplicantQueryRequest;

import fortec.common.core.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* SupplierApplicant service 接口
*
* @author chenchen
* @version 1.0
*/
public interface SupplierApplicantService extends IBaseService<SupplierApplicant> {

    List<SupplierApplicant> list(SupplierApplicantQueryRequest request);


    IPage<SupplierApplicant> page(SupplierApplicantQueryRequest request);

    /**
     * 供应商保存申请为制单状态
     * @param entity
     * @return
     */
    boolean applicant(SupplierApplicant entity);

    /**
     * 供应商提交申请为待审核状态
     * @param id
     */
    void submit(String id);

    /**
     * 医院审核
     * @param id
     */
    @Transactional
    void pass(String id);

    /**
     * 取消审核
     * @param id
     * @param auditedRemark
     */
    @Transactional
    void cancel(String id,String auditedRemark);

}
    