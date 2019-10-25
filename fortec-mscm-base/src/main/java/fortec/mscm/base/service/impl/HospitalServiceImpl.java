
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.common.feign.clients.OfficeClient;
import fortec.common.feign.clients.UserClient;
import fortec.common.upms.feign.dto.OfficeDTO;
import fortec.common.upms.feign.dto.UserInfoDTO;
import fortec.common.upms.feign.vo.OfficeVO;
import fortec.common.upms.feign.vo.UserInfoVO;
import fortec.mscm.base.entity.Hospital;
import fortec.mscm.base.mapper.HospitalMapper;
import fortec.mscm.base.request.HospitalQueryRequest;
import fortec.mscm.base.service.HospitalService;
import fortec.mscm.core.consts.SerialRuleConsts;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 医院 service 实现
 *
 * @author yuntao.zhou
 * @version 1.0
 */
@AllArgsConstructor
@Slf4j
@Service
public class HospitalServiceImpl extends BaseServiceImpl<HospitalMapper, Hospital> implements HospitalService {

    private final OfficeClient officeClient;

    private final UserClient userClient;

    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCascadeById(Hospital entity) {

        // 添加用户，供应商编号作为主账户
        String hospitalCode = SerialUtils.generateCode(SerialRuleConsts.BASE_HOSPITAL_CODE);
        entity.setCode(hospitalCode);

        // 添加机构
        OfficeDTO officeDTO = new OfficeDTO();
        officeDTO.setCode(entity.getCode())
                .setName(entity.getName());
        OfficeVO result = officeClient.addForHospital(officeDTO);
        if (result == null) {
            throw new BusinessException("机构添加失败");
        }

        entity.setOfficeId(result.getId());




        UserInfoDTO userDTO = new UserInfoDTO();
        userDTO.setOfficeId(result.getId())
                .setLoginKey(hospitalCode)
                .setNickname(entity.getName())
                .setEmail(entity.getEmail())
                .setMobile(entity.getPhone())
                .setRoles(new String[]{"hospital_manager"})
                .setRemark("医院" + entity.getName() + "主账号");
        UserInfoVO vo = userClient.addUser(userDTO);
        if (vo == null) {
            throw new BusinessException("用户添加失败");
        }
        return super.saveCascadeById(entity);
    }

    @Override
    public boolean updateCascadeById(Hospital entity) {
        entity.setCode(null).setOfficeId(null);
        return super.updateCascadeById(entity);
    }


    @Override
    public Hospital findByOfficeId(String officeId) {
        return this.getOne(Wrappers.<Hospital>query().eq("office_id",officeId));
    }

    @Override
    public IPage<Hospital> page(HospitalQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Hospital>query()
                .like(StringUtils.isNotBlank(request.getCode()), "code", request.getCode())
                .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                .orderByDesc("gmt_modified"));
        return page;
    }

    @Override
    public IPage<Hospital> pageForHospital(HospitalQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Hospital>query()
                .eq(StringUtils.isNotBlank(UserUtils.getHospitalId()), "id", UserUtils.getHospitalId())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public List<Hospital> list(HospitalQueryRequest request) {
        List<Hospital> list = this.list(Wrappers.<Hospital>query()
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<Hospital> pageByKeywords(HospitalQueryRequest request,String keywords) {
        IPage page = this.page(request.getPage(), Wrappers.<Hospital>query()
                .like(StringUtils.isNotBlank(keywords), "name", keywords)
                .orderByDesc("gmt_modified")
        );
        return page;
    }

}
    