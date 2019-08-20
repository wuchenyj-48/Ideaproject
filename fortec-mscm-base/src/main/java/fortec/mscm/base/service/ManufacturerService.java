
package fortec.mscm.base.service;

import fortec.mscm.base.entity.Manufacturer;

import fortec.common.core.service.IBaseService;



/**
* 厂商 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface ManufacturerService extends IBaseService<Manufacturer> {

    /**
     * 同一供应商下，社会信用代码唯一
     * @param entity
     * @return
     */
    boolean add(Manufacturer entity);

    boolean update(Manufacturer entity);

}
    