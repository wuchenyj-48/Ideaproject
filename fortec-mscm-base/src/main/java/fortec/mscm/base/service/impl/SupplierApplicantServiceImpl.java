
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * SupplierApplicant service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@Slf4j
@Service
public class SupplierApplicantServiceImpl extends BaseServiceImpl<SupplierApplicantMapper, SupplierApplicant> implements SupplierApplicantService {
    @Autowired
    HospitalSupplierService hospitalSupplierService;
    @Autowired
    SupplierApplicantService supplierApplicantService;

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
        /*IPage page = this.page(request.getPage(), Wrappers.<SupplierApplicant>query()
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .eq(request.getStatus() != null, "status", request.getStatus())
                .orderByDesc("gmt_modified")
        );*/
        return this.baseMapper.page(request.getPage(), request);
    }

    @Override
    public boolean applicant(SupplierApplicant entity) {

        //申请表中是否存在
        SupplierApplicant applicantServiceOne = supplierApplicantService.getOne(Wrappers.<SupplierApplicant>query()
                .eq("hospital_id", entity.getHospitalId())
                .eq("supplier_id", "1150667601773346818")
                .notIn("status",SupplierApplicant.STATUS_CANCELED,SupplierApplicant.STATUS_UNSUBMIT)
        );
        if (applicantServiceOne != null) {
            throw new BusinessException("不可重复申请", null);
        }
        //关系表中是否存在
        checkExist(entity);
        //供应商，单据号，单据状态
        entity.setSupplierId("1150667601773346818")
                .setCode("SA" + StringUtils.getRandomNum(13))
                .setStatus(SupplierApplicant.STATUS_UNSUBMIT);
        boolean save = supplierApplicantService.saveOrUpdate(entity);
        return save;
    }

    @Override
    public void submit(String id) {
        //状态是否是制单状态
        SupplierApplicant supplierApplicant = supplierApplicantService.getById(id);
        if (supplierApplicant.getStatus() != SupplierApplicant.STATUS_UNSUBMIT){
            throw new BusinessException("当前状态不支持提交");
        }
        //关系表中是否存在
        checkExist(supplierApplicant);
        //修改状态为提交待审核
        supplierApplicant.setStatus(SupplierApplicant.STATUS_SUBMITED);
        this.updateById(supplierApplicant);
    }

    @Override
    public void pass(String id) {
        //状态是否是提交待审核
        SupplierApplicant supplierApplicant = supplierApplicantService.getById(id);
        if (supplierApplicant.getStatus() != SupplierApplicant.STATUS_SUBMITED) {
            throw new BusinessException("当前状态不支持审核");
        }
        //关系表中是否存在
        checkExist(supplierApplicant);
        //修改状态为已审核
        supplierApplicant
                .setStatus(SupplierApplicant.STATUS_PASSED)
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setGmtAudited(new Date());
        this.updateById(supplierApplicant);
        //添加信息到关系表
        HospitalSupplier hospitalSupplier = new HospitalSupplier();
        BeanUtils.copyProperties(supplierApplicant,hospitalSupplier);
        hospitalSupplierService.save(hospitalSupplier);
    }

    @Override
    public void cancel(String id, String auditedRemark) {
        //状态是否是提交待审核
        SupplierApplicant supplierApplicant = supplierApplicantService.getById(id);
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

    /**
     * 关系表中查找并返回
     *
     * @param entity
     * @return
     */
    public void checkExist(SupplierApplicant entity) {
        //关系表中是否存在
        HospitalSupplier one = hospitalSupplierService.getOne(
                Wrappers.<HospitalSupplier>query()
                        .eq("hospital_id", entity.getHospitalId())
                        .eq("supplier_id", "1150667601773346818")
        );
        if (one != null) {
            throw new BusinessException("关系已存在", null);
        }
    }
}
    