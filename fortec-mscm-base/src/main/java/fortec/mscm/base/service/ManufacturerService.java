
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.Manufacturer;

import fortec.common.core.service.IBaseService;
import fortec.mscm.base.request.ManufacturerQueryRequest;

import java.util.List;


/**
* 厂商 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface ManufacturerService extends IBaseService<Manufacturer> {

    IPage<Manufacturer> page(ManufacturerQueryRequest request);

    List<Manufacturer> list(ManufacturerQueryRequest request);

    /**
     * 同一供应商下，社会信用代码唯一
     * @param entity
     * @return
     */
    boolean add(Manufacturer entity);

    boolean update(Manufacturer entity);

    /**
     * 供应商下属厂商，关键字搜索
     * @param request
     * @param keywords
     * @return
     */
    IPage<Manufacturer> pageByKeywords(ManufacturerQueryRequest request,String keywords);

}
    