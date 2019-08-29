
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.msg.domain.SceneMessage;
import fortec.common.core.msg.enums.ReceiverType;
import fortec.common.core.msg.provider.MsgPushProvider;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.DateUtils;
import fortec.common.core.utils.SecurityUtils;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.Hospital;
import fortec.mscm.base.entity.HospitalSupplier;
import fortec.mscm.base.entity.Supplier;
import fortec.mscm.base.entity.SupplierApplicant;
import fortec.mscm.base.mapper.SupplierApplicantMapper;
import fortec.mscm.base.request.SupplierApplicantQueryRequest;
import fortec.mscm.base.service.HospitalService;
import fortec.mscm.base.service.HospitalSupplierService;
import fortec.mscm.base.service.SupplierApplicantService;
import fortec.mscm.base.service.SupplierService;
import fortec.mscm.core.consts.MsgConsts;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * SupplierApplicant service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@AllArgsConstructor
@Slf4j
@Service
public class SupplierApplicantServiceImpl extends BaseServiceImpl<SupplierApplicantMapper, SupplierApplicant> implements SupplierApplicantService {

    private final HospitalSupplierService hospitalSupplierService;

    private final SupplierService supplierService;

    private final MsgPushProvider msgPushProvider;

    private final HospitalService hospitalService;

    @Override
    public List<SupplierApplicant> list(SupplierApplicantQueryRequest request) {
        List<SupplierApplicant> list = this.list(Wrappers.<SupplierApplicant>query()
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .eq(request.getStatus() != null, "status", request.getStatus())
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<SupplierApplicant> page(SupplierApplicantQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.page(request.getPage(), request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean applicant(SupplierApplicant entity) {

        //申请表中是否存在
        SupplierApplicant applicantServiceOne = this.getOne(Wrappers.<SupplierApplicant>query()
                .eq("hospital_id", entity.getHospitalId())
                .eq("supplier_id", UserUtils.getSupplierId())
                .notIn("status", SupplierApplicant.STATUS_CANCELED, SupplierApplicant.STATUS_UNSUBMIT)
        );
        if (applicantServiceOne != null) {
            throw new BusinessException("不可重复申请", null);
        }

        //关系表中是否存在
        assertHasExist(entity);

        //供应商，单据号，单据状态
        entity.setSupplierId(UserUtils.getUser().getSupplierId())
//                .setCode(SerialUtils.generateCode("base_supplier_applicant_code"))
                .setCode(StringUtils.getRandomStr(20))
                .setStatus(SupplierApplicant.STATUS_UNSUBMIT);
        return this.saveOrUpdate(entity);
    }

    @Override
    public void submit(String id) {

        SupplierApplicant supplierApplicant = this.getById(id);
        if (supplierApplicant == null){
            return;
        }

        //状态是否是制单状态
        if (supplierApplicant.getStatus() != SupplierApplicant.STATUS_UNSUBMIT) {
            throw new BusinessException("当前状态不支持提交");
        }
        //关系表中是否存在
        assertHasExist(supplierApplicant);

        //修改状态为提交待审核
        SupplierApplicant tmp = new SupplierApplicant();
        tmp.setStatus(SupplierApplicant.STATUS_SUBMITED).setId(supplierApplicant.getId());
        this.updateById(tmp);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pass(String id) {

        SupplierApplicant supplierApplicant = this.getById(id);
        if (supplierApplicant == null){
            return;
        }

        //状态是否是提交待审核
        if (supplierApplicant.getStatus() != SupplierApplicant.STATUS_SUBMITED) {
            throw new BusinessException("当前状态不支持审核");
        }

        //关系表中是否存在
        assertHasExist(supplierApplicant);

        //修改状态为已审核
        SupplierApplicant applicant = new SupplierApplicant();
        applicant.setStatus(SupplierApplicant.STATUS_PASSED)
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setGmtAudited(new Date());
        this.updateById(applicant);

        //添加信息到关系表
        HospitalSupplier hospitalSupplier = new HospitalSupplier();
        BeanUtils.copyProperties(supplierApplicant, hospitalSupplier);
        hospitalSupplier.setInactive(HospitalSupplier.DISABLE);
        hospitalSupplierService.save(hospitalSupplier);

        //发送审核消息给供应商
        Supplier supplier = supplierService.getById(supplierApplicant.getSupplierId());
        Hospital hospital = hospitalService.getById(supplierApplicant.getHospitalId());

        HashMap<String, Object> params = Maps.newHashMap();
        params.put("hospital_name", hospital.getName());
        params.put("send_date", DateUtils.format(new Date(), "yyyy-MM-dd"));

        SceneMessage message = new SceneMessage();
        message.setSceneCode(MsgConsts.SCENE_SUPPLIER_APP_SUCCESS).setReceiver(supplier.getCode())
                .setReceiverType(ReceiverType.USER).params(params);

        msgPushProvider.push(message);

    }

    @Override
    public void cancel(String id, String auditedRemark) {

        SupplierApplicant supplierApplicant = this.getById(id);

        if (supplierApplicant == null){
            return;
        }

        //状态是否是提交待审核
        if (supplierApplicant.getStatus() != SupplierApplicant.STATUS_SUBMITED) {
            throw new BusinessException("当前状态不支持取消", null);
        }

        //修改状态为已取消
        SupplierApplicant applicant = new SupplierApplicant();
        applicant.setStatus(SupplierApplicant.STATUS_CANCELED)
                .setGmtAudited(new Date())
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setAuditedRemark(auditedRemark);
        this.updateById(applicant);
    }

    @Override
    public IPage<SupplierApplicant> pageAudit(SupplierApplicantQueryRequest request) {
        request.setHospitalId(UserUtils.getHospitalId());
        return this.baseMapper.pageAudit(request.getPage(), request);
    }


    /**
     * 关系表中查找并返回
     *
     * @param entity
     * @return
     */
    public void assertHasExist(SupplierApplicant entity) {
        //关系表中是否存在
        HospitalSupplier one = hospitalSupplierService.getOne(
                Wrappers.<HospitalSupplier>query()
                        .eq("hospital_id", entity.getHospitalId())
                        .eq("supplier_id", entity.getSupplierId())
        );
        if (one != null) {
            throw new BusinessException("关系已存在", null);
        }
    }
}
    