
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.MaterialApplicant;
import fortec.mscm.base.entity.MaterialApplicantItem;
import fortec.mscm.base.mapper.MaterialApplicantMapper;
import fortec.mscm.base.request.MaterialApplicantQueryRequest;
import fortec.mscm.base.service.MaterialApplicantItemService;
import fortec.mscm.base.service.MaterialApplicantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
* MaterialApplicant service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@AllArgsConstructor
@Service
public class MaterialApplicantServiceImpl extends BaseServiceImpl<MaterialApplicantMapper, MaterialApplicant> implements MaterialApplicantService {


    private final MaterialApplicantItemService materialApplicantItemService;

    @Override
    public boolean removeCascadeById(Serializable id) {
        materialApplicantItemService.remove(Wrappers.<MaterialApplicantItem>query().eq("applicant_id", id));
        return super.removeById(id);
    }

    @Override
    public List<MaterialApplicant> list(MaterialApplicantQueryRequest request) {
        List<MaterialApplicant> list = this.list(Wrappers.<MaterialApplicant>query()
                .like(request.getHospitalId() != null, "hospital_id", request.getHospitalId())
                .like(request.getSupplierId() != null, "supplier_id", request.getSupplierId())
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .eq(request.getStatus() != null, "status", request.getStatus())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<MaterialApplicant> page(MaterialApplicantQueryRequest request) {
        /*IPage page = this.page(request.getPage(), Wrappers.<MaterialApplicant>query()
                .like(request.getHospitalId() != null, "hospital_id", request.getHospitalId())
                .like(request.getSupplierId() != null, "supplier_id", request.getSupplierId())
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .eq(request.getStatus() != null, "status", request.getStatus())
            .orderByDesc("gmt_modified")
        );*/
        return this.baseMapper.page(request.getPage(),request);
    }

    @Override
    public boolean saveHospital(MaterialApplicant entity) {
        //申请表中已存在

        //供应商id，单据号，单据状态，医院id
        entity.setSupplierId("1150667601773346818")
                .setCode("MA"+StringUtils.getRandomNum(13))
                .setStatus(MaterialApplicant.STATUS_UNSUBMIT);
        return this.save(entity);

    }

}
    