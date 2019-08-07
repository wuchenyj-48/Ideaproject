
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.feign.clients.OfficeClient;
import fortec.common.feign.clients.UserClient;
import fortec.common.feign.dto.OfficeDTO;
import fortec.common.feign.dto.UserDTO;
import fortec.common.feign.model.CommonResult;
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
                .setName(entity.getName())
                .setType("HOSPITAL");
        CommonResult<OfficeDTO> result = officeClient.addOffice(officeDTO);
        if (!result.isSuccess()) {
            throw new BusinessException(result.getMsg());
        }

        officeDTO = result.getData();
        entity.setOfficeId(officeDTO.getId());


        // 添加用户，供应商编号作为主账户
        String hospitalCode = SerialUtils.generateCode("base_hospital_code");
        entity.setCode(hospitalCode);

        UserDTO userDTO = new UserDTO();
        userDTO.setOfficeId(officeDTO.getId())
                .setUserKey(hospitalCode)
                .setUserName(entity.getName())
                .setUserEmail(entity.getEmail())
                .setUserMobile(entity.getPhone())
                .setUserRemark("医院" + entity.getName() + "主账号");
        CommonResult<UserDTO> userResult = userClient.addUser(userDTO);
        if (!userResult.isSuccess()) {
            throw new BusinessException(userResult.getMsg());
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
    