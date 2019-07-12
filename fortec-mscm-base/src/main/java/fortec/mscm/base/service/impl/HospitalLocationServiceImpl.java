
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.base.entity.HospitalLocation;
import fortec.mscm.base.mapper.HospitalLocationMapper;
import fortec.mscm.base.request.HospitalLocationQueryRequest;
import fortec.mscm.base.service.HospitalLocationService;
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
public class HospitalLocationServiceImpl extends BaseServiceImpl<HospitalLocationMapper, HospitalLocation> implements HospitalLocationService {


    @Override
    public List<HospitalLocation> list(HospitalLocationQueryRequest request) {
        List<HospitalLocation> list = this.list(Wrappers.<HospitalLocation>query()
                .eq(request.getHospitalId() != null, "hospital_id", request.getHospitalId())
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<HospitalLocation> page(HospitalLocationQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<HospitalLocation>query()
                .eq(request.getHospitalId() != null, "hospital_id", request.getHospitalId())
                .orderByDesc("gmt_modified")
        );
        return page;
    }
}
    