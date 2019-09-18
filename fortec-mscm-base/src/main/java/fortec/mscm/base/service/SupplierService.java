
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.Supplier;

import fortec.common.core.service.IBaseService;
import fortec.mscm.base.request.SupplierQueryRequest;

import java.util.List;


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

    IPage<Supplier> page(SupplierQueryRequest request);

    /**
     * 获取当前供应商
     * @param request
     * @return
     */
    IPage<Supplier> pageForSupplier(SupplierQueryRequest request);

    List<Supplier> list(SupplierQueryRequest request);

    /**
     * 获取供应商，关键字搜索
     * @param request
     * @param keywords
     * @return
     */
    IPage<Supplier> pageByKeywords(SupplierQueryRequest request,String keywords);

}
    