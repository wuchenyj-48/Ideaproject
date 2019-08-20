
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.SecurityUtils;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.HospitalSupplier;
import fortec.mscm.base.entity.SupplierApplicant;
import fortec.mscm.base.mapper.SupplierApplicantMapper;
import fortec.mscm.base.request.SupplierApplicantQueryRequest;
import fortec.mscm.base.service.HospitalSupplierService;
import fortec.mscm.base.service.SupplierApplicantService;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        //状态是否是制单状态
        SupplierApplicant supplierApplicant = this.getById(id);
        if (supplierApplicant.getStatus() != SupplierApplicant.STATUS_UNSUBMIT) {
            throw new BusinessException("当前状态不支持提交");
        }
        //关系表中是否存在
        assertHasExist(supplierApplicant);

        //修改状态为提交待审核
        supplierApplicant.setStatus(SupplierApplicant.STATUS_SUBMITED);
        this.updateById(supplierApplicant);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pass(String id) {
        //状态是否是提交待审核
        SupplierApplicant supplierApplicant = this.getById(id);
        if (supplierApplicant.getStatus() != SupplierApplicant.STATUS_SUBMITED) {
            throw new BusinessException("当前状态不支持审核");
        }

        assertHasExist(supplierApplicant);

        //修改状态为已审核
        supplierApplicant
                .setStatus(SupplierApplicant.STATUS_PASSED)
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setGmtAudited(new Date());
        this.updateById(supplierApplicant);

        //添加信息到关系表
        HospitalSupplier hospitalSupplier = new HospitalSupplier();
        BeanUtils.copyProperties(supplierApplicant, hospitalSupplier);
        hospitalSupplier.setInactive(HospitalSupplier.DISABLE);
        hospitalSupplierService.save(hospitalSupplier);
    }

    @Override
    public void cancel(String id, String auditedRemark) {
        //状态是否是提交待审核
        SupplierApplicant supplierApplicant = this.getById(id);
        if (supplierApplicant.getStatus() != SupplierApplicant.STATUS_SUBMITED) {
            throw new BusinessException("当前状态不支持取消", null);
        }
        //修改状态为已取消
        supplierApplicant.setStatus(SupplierApplicant.STATUS_CANCELED)
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setGmtAudited(new Date())
                .setAuditedRemark(auditedRemark);
        this.updateById(supplierApplicant);
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
    