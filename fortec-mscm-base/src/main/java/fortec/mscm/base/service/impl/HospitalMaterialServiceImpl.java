
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.HospitalMaterial;
import fortec.mscm.base.mapper.HospitalMaterialMapper;
import fortec.mscm.base.request.HospitalMaterialQueryRequest;
import fortec.mscm.base.service.HospitalMaterialService;
import fortec.mscm.security.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        request.setHospitalId(UserUtils.getHospitalId());
        return this.baseMapper.page(request.getPage(), request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void active(String id) {
        HospitalMaterial hm = this.getById(id);
        if (hm == null) {
            return;
        }

        //当前状态是否是停用状态
        if (hm.getInactive() != HospitalMaterial.DEACTIVATE) {
            return;
        }

        //修改状态为正常状态
        hm.setInactive(HospitalMaterial.ACTIVATE);
        this.updateById(hm);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deactive(String id) {
        HospitalMaterial hm = this.getById(id);
        if (hm == null) {
            return;
        }

        //当前状态是否是正常状态
        if (hm.getInactive() != HospitalMaterial.ACTIVATE) {
            return;
        }

        //修改状态为停用状态
        hm.setInactive(HospitalMaterial.DEACTIVATE);
        this.updateById(hm);
    }
}
    