
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.google.common.collect.Maps;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.global.GlobalParamService;
import fortec.common.core.msg.domain.SceneMessage;
import fortec.common.core.msg.enums.ReceiverType;
import fortec.common.core.msg.provider.ISceneMsgPushProvider;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.DateUtils;
import fortec.common.core.utils.SecurityUtils;
import fortec.common.core.utils.StringUtils;
import fortec.common.feign.clients.OfficeClient;
import fortec.common.feign.clients.UserClient;
import fortec.common.upms.feign.dto.OfficeDTO;
import fortec.common.upms.feign.dto.UserInfoDTO;
import fortec.common.upms.feign.vo.OfficeVO;
import fortec.common.upms.feign.vo.UserInfoVO;
import fortec.mscm.base.entity.Supplier;
import fortec.mscm.base.entity.SupplierRegist;
import fortec.mscm.base.mapper.SupplierRegistMapper;
import fortec.mscm.base.request.SupplierRegistQueryRequest;
import fortec.mscm.base.service.SupplierRegistService;
import fortec.mscm.base.service.SupplierService;
import fortec.mscm.base.utils.PinYinUtils;
import fortec.mscm.core.consts.MsgConsts;
import fortec.mscm.core.consts.SerialRuleConsts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * service 实现
 *
 * @author chenchen
 * @version 1.0
 */

@Slf4j
@AllArgsConstructor
@Service
public class SupplierRegistServiceImpl extends BaseServiceImpl<SupplierRegistMapper, SupplierRegist> implements SupplierRegistService {

    private final SupplierService supplierService;

    private final UserClient userClient;

    private final OfficeClient officeClient;

    private final ISceneMsgPushProvider ISceneMsgPushProvider;

    private final GlobalParamService globalParamService;

    /**
     * 检查申请人手机号是否可用
     * @param phone
     * @return
     */
    @Override
    public boolean checkPhoneValid(String phone) {
        return this.count(Wrappers.<SupplierRegist>query().eq("applicant_mobile", phone)) == 0;
    }


    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    @Override
    public void pass(String id) {

        SupplierRegist regist = this.getById(id);

        if (regist == null) {
            throw new BusinessException("供应商注册信息不存在", null);
        }

        //判断当前状态是否是待审核
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


        // 添加机构
        OfficeDTO officeDTO = new OfficeDTO();
        officeDTO.setCode(regist.getCompanyCode())
                .setName(regist.getName());
        OfficeVO officeVO = officeClient.addForSupplier(officeDTO);
        if (officeVO == null) {
            throw new BusinessException("机构添加失败");
        }


        // 添加用户，供应商编号作为主账户
        String supplierCode = SerialUtils.generateCode(SerialRuleConsts.BASE_SUPPLIER_CODE);

        UserInfoDTO infoDTO = new UserInfoDTO();
        infoDTO.setOfficeId(officeVO.getId())
                .setLoginKey(supplierCode)
                .setNickname(regist.getName())
                .setEmail(regist.getApplicantEmail())
                .setMobile(regist.getApplicantMobile())
                .setRemark("供应商" + regist.getName() + "主账号");
        UserInfoVO vo = userClient.addUser(infoDTO);
        if (vo == null) {
            throw new BusinessException("主用户添加失败");
        }

        // 添加正式信息到供应商表
        supplier = new Supplier();
        BeanUtils.copyProperties(regist, supplier);
        supplier
                .setOfficeId(officeVO.getId())
                .setMobile(regist.getApplicantMobile())
                .setPinyin(PinYinUtils.getAlpha(supplier.getName()))
                .setContactor(regist.getApplicant())
                .setEmail(regist.getApplicantEmail())
                .setCode(supplierCode)
                .setId(null);
        supplierService.save(supplier);

        /**
         * 发送通知邮件
         */
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("account", infoDTO.getLoginKey());
        params.put("password", "123456");
        params.put("login_url", globalParamService.getProperty("upms.user.login.url", "http://localhost:5060/pages/login.html"));
        params.put("send_date", DateUtils.format(new Date(), "yyyy-MM-dd"));

        SceneMessage sm = new SceneMessage();
        sm.setSceneCode(MsgConsts.SCENE_SUPPLLIER_REG_SUCCESS).setReceiver(infoDTO.getLoginKey())
                .setReceiverType(ReceiverType.USER).params(params);
        ISceneMsgPushProvider.push(sm);
    }

    @Override
    public void cancel(String id, String reason) {

        SupplierRegist supplierRegist = this.getById(id);

        if (supplierRegist == null){
            return;
        }

        //判断当前状态是否待审核
        if (supplierRegist.getAuditStatus() != SupplierRegist.AUDIT_STATUS_SUBMITED) {
            throw new BusinessException("当前状态不支持取消", null);
        }

        SupplierRegist regist = new SupplierRegist();
        regist.setAuditStatus(SupplierRegist.AUDIT_STATUS_CANCELED)
                .setAuditor(SecurityUtils.getCurrentUser().getId())
                .setGmtAudited(new Date())
                .setCancelReason(reason)
                .setId(supplierRegist.getId())
        ;
        this.updateById(regist);
    }

    @Override
    public IPage<SupplierRegist> page(SupplierRegistQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<SupplierRegist>query()
                .like(StringUtils.isNotBlank(request.getCompanyCode()), "company_code", request.getCompanyCode())
                .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                .eq(request.getIsDrug() != null, "is_drug", request.getIsDrug())
                .eq(request.getIsConsumable() != null, "is_consumable", request.getIsConsumable())
                .eq(request.getIsReagent() != null, "is_reagent", request.getIsReagent())
                .eq(request.getAstatus() != null, "audit_status", request.getAstatus())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public List<SupplierRegist> list(SupplierRegistQueryRequest request) {
        List<SupplierRegist> list = this.list(Wrappers.<SupplierRegist>query()
                .orderByDesc("gmt_modified")
        );
        return list;
    }


}
    