
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.HospitalSupplier;
import fortec.mscm.base.mapper.HospitalSupplierMapper;
import fortec.mscm.base.request.HospitalSupplierQueryRequest;
import fortec.mscm.base.service.HospitalSupplierService;
import fortec.mscm.security.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* HospitalSupplier service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class HospitalSupplierServiceImpl extends BaseServiceImpl<HospitalSupplierMapper, HospitalSupplier> implements HospitalSupplierService {

    @Override
    public List<HospitalSupplier> list(HospitalSupplierQueryRequest request) {
        List<HospitalSupplier> list = this.list(Wrappers.<HospitalSupplier>query()
                .like(StringUtils.isNotBlank(request.getHospitalId()), "hospital_id", request.getHospitalId())
                .like(StringUtils.isNotBlank(request.getSupplierId()), "supplier_id", request.getSupplierId())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<HospitalSupplier> page(HospitalSupplierQueryRequest request) {
        return this.baseMapper.page(request.getPage(),request);

    }

    @Override
    public IPage<HospitalSupplier> pageForHospital(HospitalSupplierQueryRequest request) {
        request.setHospitalId(UserUtils.getHospitalId());
        return this.baseMapper.page(request.getPage(),request);
    }

    @Override
    public IPage<HospitalSupplier> pageForSupplier(HospitalSupplierQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.page(request.getPage(),request);
    }

    @Override
    public IPage<HospitalSupplier> pageByKeywords(HospitalSupplierQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.pageByKeywords(request.getPage(),request);
    }

    @Override
    public IPage<HospitalSupplier> pageByKeywordsForHospital(HospitalSupplierQueryRequest request) {
        request.setHospitalId(UserUtils.getHospitalId());
        return this.baseMapper.pageByKeywordsForHospital(request.getPage(),request);
    }

    @Override
    public void enable(String id) {
        HospitalSupplier hs = this.getById(id);
        if (hs == null) {
            return;
        }

        //当前状态是否是停用状态
        if (hs.getInactive() != HospitalSupplier.DISABLE) {
            return;
        }

        //修改状态为正常状态
        HospitalSupplier hospitalSupplier = new HospitalSupplier();
        hospitalSupplier.setInactive(HospitalSupplier.ENABLE).setId(hs.getId());
        this.updateById(hospitalSupplier);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void disable(String id) {
        HospitalSupplier hs = this.getById(id);
        if (hs == null) {
            return;
        }

        //当前状态是否是正常状态
        if (hs.getInactive() != HospitalSupplier.ENABLE) {
            return;
        }

        //修改状态为停用状态
        HospitalSupplier hospitalSupplier = new HospitalSupplier();
        hospitalSupplier.setInactive(HospitalSupplier.DISABLE).setId(hs.getId());
        this.updateById(hospitalSupplier);
    }


}
    