
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.SecurityUtils;
import fortec.mscm.base.entity.Supplier;
import fortec.mscm.base.entity.SupplierRegist;
import fortec.mscm.base.mapper.SupplierRegistMapper;
import fortec.mscm.base.service.SupplierRegistService;
import fortec.mscm.base.service.SupplierService;
import fortec.mscm.base.utils.PinYinUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@Slf4j
@Service
public class SupplierRegistServiceImpl extends BaseServiceImpl<SupplierRegistMapper, SupplierRegist> implements SupplierRegistService {

    @Autowired
    SupplierService supplierService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pass(String id) {

        SupplierRegist regist = this.getById(id);
        if (regist == null) {
            throw new BusinessException("供应商注册信息不存在", null);
        }
        if (regist.getAuditStatus() != SupplierRegist.AUDIT_STATUS_SUBMITED) {
            throw new BusinessException("当前状态不支持审核", null);
        }
        Supplier supplier = supplierService.getOne(Wrappers.<Supplier>query().eq("company_code", regist.getCompanyCode()));
        if (supplier != null) {
            throw new BusinessException("统一社会信用" + regist.getCompanyCode() + "已经为正式供应商", null);
        }

        SupplierRegist sr = new SupplierRegist();
        sr.setAuditStatus(SupplierRegist.AUDIT_STATUS_PASSED)
                .setGmtAudited(new Date())
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setId(id);

        this.updateById(sr);

        // 添加正式信息到供应商表
        supplier = new Supplier();
        BeanUtils.copyProperties(regist, supplier);
        supplier
                .setMobile(regist.getApplicantMobile())
                .setPinyin(PinYinUtils.getAlpha(supplier.getName()))
                .setContactor(regist.getApplicant())
                .setEmail(regist.getApplicantEmail())
                .setCode(SerialUtils.generateCode("base_supplier_code"))
                .setId(null);


        supplierService.save(supplier);


        // TODO 添加正式机构 和 用户

    }

    @Override
    public void cancel(String id, String reason) {
        SupplierRegist supplierRegist = this.getById(id);
        if (supplierRegist.getAuditStatus() != SupplierRegist.AUDIT_STATUS_SUBMITED) {
            throw new BusinessException("当前状态不支持取消", null);
        }
        supplierRegist.setAuditStatus(SupplierRegist.AUDIT_STATUS_CANCELED)
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setGmtAudited(new Date())
                .setCancelReason(reason);
        this.updateById(supplierRegist);
    }


}
    