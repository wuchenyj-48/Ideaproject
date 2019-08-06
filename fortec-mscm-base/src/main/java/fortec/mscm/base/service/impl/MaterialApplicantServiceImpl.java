
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.SecurityUtils;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.core.consts.CommonConsts;
import fortec.mscm.base.entity.*;
import fortec.mscm.base.mapper.MaterialApplicantMapper;
import fortec.mscm.base.request.MaterialApplicantQueryRequest;
import fortec.mscm.base.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
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

    private final HospitalMaterialService hospitalMaterialService;

    private final MaterialApplicantItemService materialApplicantItemService;

    private final HospitalService hospitalService;

    private final MaterialSpecService materialSpecService;

    private final MaterialService materialService;


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

        return this.baseMapper.page(request.getPage(), request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveHospital(MaterialApplicant entity) {

        //供应商id，单据号，单据状态，医院id
        entity.setSupplierId(CommonConsts.SUPPLIER_ID)
                .setCode(SerialUtils.generateCode("base_material_applicant_code"))
                .setStatus(MaterialApplicant.STATUS_UNSUBMIT);
        return this.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submit(String id) {
        //当前状态是否为制单状态
        MaterialApplicant ma = this.getById(id);
        if (ma.getStatus() != MaterialApplicant.STATUS_UNSUBMIT) {
            throw new BusinessException("当前状态不是制单状态");
        }

        //商品明细是否为空
        List<MaterialApplicantItem> list = materialApplicantItemService.list(Wrappers.<MaterialApplicantItem>query()
                .eq("applicant_id", id));

        if (list.isEmpty()) {
            throw new BusinessException("明细行为空，不允许提交");
        }

        //医院商品表中是否已存在
        for (MaterialApplicantItem materialApplicantItem : list) {
            assertRelExist(ma.getHospitalId(), materialApplicantItem.getMaterialSpecId());
        }

        //修改状态为提交待审核
        ma.setStatus(MaterialApplicant.STATUS_SUBMITED);
        this.updateById(ma);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pass(String id) {

        // 当前状态是否为提交待审核
        MaterialApplicant ma = this.getById(id);
        if (ma.getStatus() != MaterialApplicant.STATUS_SUBMITED) {
            throw new BusinessException("当前状态不是待审核状态");
        }

        // 医院商品表中是否已存在
        List<MaterialApplicantItem> list = materialApplicantItemService.list(Wrappers.<MaterialApplicantItem>query()
                .eq("applicant_id", id));
        for (MaterialApplicantItem materialApplicantItem : list) {
            assertRelExist(ma.getHospitalId(), materialApplicantItem.getMaterialSpecId());
        }

        // 修改申请单状态为通过
        ma.setStatus(MaterialApplicant.STATUS_PASSED)
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setGmtAudited(new Date());
        this.updateById(ma);

        // 向医院商品插入数据
        List<HospitalMaterial> hms = Lists.newArrayListWithCapacity(list.size());
        HospitalMaterial hm;
        Hospital hospital = hospitalService.getById(ma.getHospitalId());

        for (MaterialApplicantItem item : list) {
            hm = new HospitalMaterial();
            Material material = materialService.getById(item.getMaterialId());
            MaterialSpec materialSpec = materialSpecService.getById(item.getMaterialSpecId());

            if (material == null || materialSpec == null){
                throw new BusinessException("商品不存在");
            }

            hm.setHospitalId(ma.getHospitalId())
                    .setMaterialId(item.getMaterialId())
                    .setMaterialSpecId(item.getMaterialSpecId())
                    .setMaterialSpec(materialSpec.getMaterialSpec())
                    .setMaterialName(material.getMaterialName())
                    .setMaterialCode(materialSpec.getInputCode())
                    .setHospitalName(hospital.getName())
                    .setMaterialTradeName(material.getMaterialTradeName())
                    .setPrice(materialSpec.getPrice())
                    .setMiniumOrderQty(0.0)
                    .setMiniumRequestQty(0.0)
                    .setInactive(HospitalMaterial.DEACTIVATE);
            hms.add(hm);
        }

        // 批量保存
        hospitalMaterialService.saveBatch(hms);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancel(String id,String reason) {

        //当前状态是否为提交待审核
        MaterialApplicant ma = this.getById(id);
        if (ma.getStatus() != MaterialApplicant.STATUS_SUBMITED){
            throw new BusinessException("当前状态不是待审核状态");
        }

        //医院商品表中是否已存在
        List<MaterialApplicantItem> list = materialApplicantItemService.list(Wrappers.<MaterialApplicantItem>query()
                .eq("applicant_id", id));
        for (MaterialApplicantItem materialApplicantItem : list) {
            assertRelExist(ma.getHospitalId(), materialApplicantItem.getMaterialSpecId());
        }

        //修改申请单状态为已取消，并提交
        ma.setStatus(MaterialApplicant.STATUS_CANCELED)
                .setAuditedRemark(reason)
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setGmtAudited(new Date());
        this.updateById(ma);
    }


    @Override
    public void assertRelExist(String hospitalId, String materialSpecId) {
        //关系表中是否存在
        HospitalMaterial one = hospitalMaterialService.getOne(
                Wrappers.<HospitalMaterial>query()
                        .eq("hospital_id", hospitalId)
                        .eq("material_spec_id", materialSpecId)
        );
        if (one == null) {
            return;
        }
        Hospital h = hospitalService.getById(hospitalId);
        MaterialSpec ms = materialSpecService.getById(materialSpecId);
        throw new BusinessException(h.getName() + "已存在" + ms.getMaterialSpec()+"了呀！");
    }


}
    