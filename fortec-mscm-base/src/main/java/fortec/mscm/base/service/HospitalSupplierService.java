
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.HospitalSupplier;
import fortec.mscm.base.request.HospitalSupplierQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* HospitalSupplier service 接口
*
* @author chenchen
* @version 1.0
*/
public interface HospitalSupplierService extends IBaseService<HospitalSupplier> {

    List<HospitalSupplier> list(HospitalSupplierQueryRequest request);


    IPage<HospitalSupplier> page(HospitalSupplierQueryRequest request);

    /**
     * 根据供应商id获取医院
     * @param request
     * @return
     */
    IPage<HospitalSupplier> pageByKeywords(HospitalSupplierQueryRequest request);

    /**
     * 将医院供应商状态设为正常
     * @param id
     */
    void enable(String id);

    /**
     * 将医院供应商状态设为停用
     * @param id
     */
    void disable(String id);

}
    