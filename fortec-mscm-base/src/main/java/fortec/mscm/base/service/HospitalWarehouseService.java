
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.IBaseService;
import fortec.mscm.base.entity.HospitalWarehouse;
import fortec.mscm.base.request.HospitalWarehouseQueryRequest;

import java.util.List;


/**
* 医院收货地点 service 接口
*
* @author yuntao.zhou
* @version 1.0
*/
public interface HospitalWarehouseService extends IBaseService<HospitalWarehouse> {


    List<HospitalWarehouse> list(HospitalWarehouseQueryRequest request);


    IPage<HospitalWarehouse> page(HospitalWarehouseQueryRequest request);
}
    