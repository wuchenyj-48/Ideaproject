
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.msg.domain.SceneMessage;
import fortec.common.core.msg.enums.ReceiverType;
import fortec.common.core.msg.provider.MsgPushProvider;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.DateUtils;
import fortec.common.core.utils.SecurityUtils;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.*;
import fortec.mscm.base.mapper.MaterialApplicantMapper;
import fortec.mscm.base.request.MaterialApplicantQueryRequest;
import fortec.mscm.base.service.*;
import fortec.mscm.core.consts.MsgConsts;
import fortec.mscm.core.consts.SerialRuleConsts;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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

    private final MsgPushProvider msgPushProvider;

    private final SupplierService supplierService;


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
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.page(request.getPage(), request);
    }

    @Override
    public IPage<MaterialApplicant> pageAudit(MaterialApplicantQueryRequest request) {
        request.setHospitalId(UserUtils.getHospitalId());
        return this.baseMapper.page(request.getPage(), request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveHospital(MaterialApplicant entity) {

        //供应商id，单据号，单据状态，医院id
        entity.setSupplierId(UserUtils.getSupplierId())
                .setCode(SerialUtils.generateCode(SerialRuleConsts.BASE_MATERIAL_APPLICANT_CODE))
                .setStatus(MaterialApplicant.STATUS_UNSUBMIT);
        return this.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submit(String id) {

        MaterialApplicant ma = this.getById(id);
        if (ma == null){
            return;
        }
        //当前状态是否为制单状态
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
        MaterialApplicant materialApplicant = new MaterialApplicant();
        materialApplicant.setStatus(MaterialApplicant.STATUS_SUBMITED).setId(ma.getId());
        this.updateById(materialApplicant);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pass(String id) {

        MaterialApplicant ma = this.getById(id);
        if (ma == null){
            return;
        }

        // 当前状态是否为提交待审核
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
        MaterialApplicant materialApplicant = new MaterialApplicant();
        materialApplicant.setStatus(MaterialApplicant.STATUS_PASSED)
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setGmtAudited(new Date())
                .setId(ma.getId());
        this.updateById(materialApplicant);

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

        //发送审核消息给供应商
        Supplier supplier = supplierService.getById(ma.getSupplierId());

        HashMap<String, Object> params = Maps.newHashMap();
        params.put("hospital_name",hospital.getName());
        params.put("code", ma.getCode());
        params.put("send_date", DateUtils.format(new Date(), "yyyy-MM-dd"));

        SceneMessage message = new SceneMessage();
        message.setSceneCode(MsgConsts.SCENE_MATERIAL_APPLICANT_SUCCESS).setReceiver(supplier.getCode())
                .setReceiverType(ReceiverType.USER).params(params);

        msgPushProvider.push(message);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancel(String id,String reason) {

        MaterialApplicant ma = this.getById(id);
        if (ma == null){
            return;
        }

        //当前状态是否为提交待审核
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
        MaterialApplicant materialApplicant = new MaterialApplicant();
        materialApplicant.setStatus(MaterialApplicant.STATUS_CANCELED)
                .setAuditedRemark(reason)
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setGmtAudited(new Date())
                .setId(ma.getId());
        this.updateById(materialApplicant);
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
    