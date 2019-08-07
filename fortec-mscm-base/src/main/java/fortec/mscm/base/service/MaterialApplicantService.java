
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.service.IBaseService;
import fortec.mscm.base.entity.MaterialApplicant;
import fortec.mscm.base.request.MaterialApplicantQueryRequest;

import java.util.List;

/**
* MaterialApplicant service 接口
*
* @author chenchen
* @version 1.0
*/
public interface MaterialApplicantService extends IBaseService<MaterialApplicant> {

    List<MaterialApplicant> list(MaterialApplicantQueryRequest request);


    IPage<MaterialApplicant> page(MaterialApplicantQueryRequest request);

    /**
     * 供货申请审核
     * @param request
     * @return
     */
    IPage<MaterialApplicant> pageAudit(MaterialApplicantQueryRequest request);

    /**
     * 获取当前供应商的关联医院
     * @param entity
     * @return
     */
    boolean saveHospital(MaterialApplicant entity);

    /**
     * 制单状态提交为待审核状态
     * @param id
     */
    void submit(String id);

    /**
     * 通过
     * @param id
     */
    void pass(String id);

    /**
     * 取消
     * @param id
     * @param reason
     */
    void cancel(String id,String reason);

    /**
     * 医院-商品规格  是否已经关联
     * @param hospitalId 医院ID
     * @param materialSpecId 商品规格ID
     * @throws BusinessException  如果关系存在，将抛出异常
     */
    void assertRelExist(String hospitalId, String materialSpecId) throws BusinessException;

}
    