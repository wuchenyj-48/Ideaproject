
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;

import fortec.mscm.base.entity.Supplier;
import fortec.mscm.base.mapper.SupplierMapper;
import fortec.mscm.base.service.SupplierService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
* 供应商 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class SupplierServiceImpl extends BaseServiceImpl<SupplierMapper, Supplier> implements SupplierService {


    @Override
    public Supplier findByOfficeId(String officeId) {
        return this.getOne(Wrappers.<Supplier>query().eq("office_id",officeId));
    }
}
    