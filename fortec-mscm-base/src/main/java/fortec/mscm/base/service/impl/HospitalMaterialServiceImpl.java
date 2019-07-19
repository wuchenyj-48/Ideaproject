
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;

import fortec.mscm.base.entity.HospitalMaterial;
import fortec.mscm.base.mapper.HospitalMaterialMapper;
import fortec.mscm.base.request.HospitalMaterialQueryRequest;
import fortec.mscm.base.service.HospitalMaterialService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
* HospitalMaterial service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class HospitalMaterialServiceImpl extends BaseServiceImpl<HospitalMaterialMapper, HospitalMaterial> implements HospitalMaterialService {

    @Override
    public List<HospitalMaterial> list(HospitalMaterialQueryRequest request) {
        List<HospitalMaterial> list = this.list(Wrappers.<HospitalMaterial>query()
                .like(request.getHospitalId() != null, "hospital_id", request.getHospitalId())
                .like(StringUtils.isNotBlank(request.getMaterialName()), "material_name", request.getMaterialName())
                .between(request.getBeginPrice() != null && request.getEndPrice() != null, "price", request.getBeginPrice(), request.getEndPrice())
                .eq(request.getInactive() != null, "inactive", request.getInactive())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<HospitalMaterial> page(HospitalMaterialQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<HospitalMaterial>query()
                .like(request.getHospitalId() != null, "hospital_id", request.getHospitalId())
                .like(StringUtils.isNotBlank(request.getMaterialName()), "material_name", request.getMaterialName())
                .between(request.getBeginPrice() != null && request.getEndPrice() != null, "price", request.getBeginPrice(), request.getEndPrice())
                .eq(request.getInactive() != null, "inactive", request.getInactive())
            .orderByDesc("gmt_modified")
        );
        return page;
    }
}
    