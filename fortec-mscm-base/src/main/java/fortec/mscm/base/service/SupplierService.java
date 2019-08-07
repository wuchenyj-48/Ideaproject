
package fortec.mscm.base.service;

import fortec.mscm.base.entity.Supplier;

import fortec.common.core.service.IBaseService;


/**
* 供应商 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface SupplierService extends IBaseService<Supplier> {

    /**
     * 根据机构ID获取供应商信息
     * @param officeId
     * @return
     */
    Supplier findByOfficeId(String officeId);

}
    