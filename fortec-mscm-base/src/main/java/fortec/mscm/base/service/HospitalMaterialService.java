
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.HospitalMaterial;
import fortec.mscm.base.request.HospitalMaterialQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* HospitalMaterial service 接口
*
* @author chenchen
* @version 1.0
*/
public interface HospitalMaterialService extends IBaseService<HospitalMaterial> {

    List<HospitalMaterial> list(HospitalMaterialQueryRequest request);


    IPage<HospitalMaterial> page(HospitalMaterialQueryRequest request);

    IPage<HospitalMaterial> pageForSupplier(HospitalMaterialQueryRequest request);

    /**
     * 将医院商品状态设为正常
     * @param id
     */
    void active(String id);

    /**
     * 将医院商品状态设为停用
     * @param id
     */
    void deactive(String id);

    /**
     * 关键字搜索医院商品
     * @param request
     * @return
     */
    IPage<HospitalMaterial> pageByKeyword(HospitalMaterialQueryRequest request);

}
    