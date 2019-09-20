
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.HospitalWarehouse;
import fortec.mscm.base.mapper.HospitalWarehouseMapper;
import fortec.mscm.base.request.HospitalWarehouseQueryRequest;
import fortec.mscm.base.service.HospitalWarehouseService;
import fortec.mscm.security.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 医院收货地点 service 实现
 *
 * @author yuntao.zhou
 * @version 1.0
 */
@Slf4j
@Service
public class HospitalWarehouseServiceImpl extends BaseServiceImpl<HospitalWarehouseMapper, HospitalWarehouse> implements HospitalWarehouseService {


    @Override
    public List<HospitalWarehouse> list(HospitalWarehouseQueryRequest request) {
        List<HospitalWarehouse> list = this.list(Wrappers.<HospitalWarehouse>query()
                .eq(request.getHospitalId() != null, "hospital_id", request.getHospitalId())
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<HospitalWarehouse> page(HospitalWarehouseQueryRequest request) {
        if (UserUtils.getUser().isHospital()) {
            request.setHospitalId(Long.valueOf(UserUtils.getHospitalId()));
        }
        IPage page = this.page(request.getPage(), Wrappers.<HospitalWarehouse>query()
                .eq(request.getHospitalId() != null, "hospital_id", request.getHospitalId())
                .and(StringUtils.isNotBlank(request.getKeywords()), o ->
                        o.like("name", request.getKeywords())
                                .or()
                                .like("code", request.getKeywords()))
                .orderByDesc("gmt_modified")
        );
        return page;
    }


}
    