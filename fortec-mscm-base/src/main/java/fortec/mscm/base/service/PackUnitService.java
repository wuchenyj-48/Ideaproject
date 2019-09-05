
package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.PackUnit;

import fortec.common.core.service.IBaseService;
import fortec.mscm.base.request.PackUnitQueryRequest;

import java.util.List;


/**
* 包装单位 service 接口
*
* @author yuntao.zhou
* @version 1.0
*/
public interface PackUnitService extends IBaseService<PackUnit> {

    IPage<PackUnit> page(PackUnitQueryRequest request);

    List<PackUnit> list(PackUnitQueryRequest request);
}
