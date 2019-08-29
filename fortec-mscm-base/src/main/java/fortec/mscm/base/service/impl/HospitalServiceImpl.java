
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.feign.clients.OfficeClient;
import fortec.common.feign.clients.UserClient;
import fortec.common.upms.feign.dto.OfficeDTO;
import fortec.common.upms.feign.dto.UserInfoDTO;
import fortec.common.upms.feign.vo.OfficeVO;
import fortec.common.upms.feign.vo.UserInfoVO;
import fortec.mscm.base.entity.Hospital;
import fortec.mscm.base.mapper.HospitalMapper;
import fortec.mscm.base.service.HospitalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

        // 添加机构
        OfficeDTO officeDTO = new OfficeDTO();
        officeDTO.setCode(entity.getCode())
                .setName(entity.getName());
        OfficeVO result = officeClient.addForHospital(officeDTO);
        if (result == null) {
            throw new BusinessException("机构添加失败");
        }

        entity.setOfficeId(result.getId());


        // 添加用户，供应商编号作为主账户
        String hospitalCode = SerialUtils.generateCode("base_hospital_code");
        entity.setCode(hospitalCode);

        UserInfoDTO userDTO = new UserInfoDTO();
        userDTO.setOfficeId(result.getId())
                .setLoginKey(hospitalCode)
                .setNickname(entity.getName())
                .setEmail(entity.getEmail())
                .setMobile(entity.getPhone())
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
}
    