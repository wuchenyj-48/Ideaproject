
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
     * 供方资格申请审核通过 提交待审核状态 修改为 已审核状态
     * @param id
     */
    @Transactional
    void pass(String id);

    /**
     * 供方资格申请审核不通过 提交待审核状态 修改为 取消状态
     * @param id
     * @param auditedRemark
     */
    @Transactional
    void cancel(String id,String auditedRemark);

    /**
     * 审核页
     * @param request
     * @return
     */
    IPage<SupplierApplicant> pageAudit(SupplierApplicantQueryRequest request);

}
    