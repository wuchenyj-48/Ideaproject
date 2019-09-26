
package fortec.mscm.settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.core.consts.SerialRuleConsts;
import fortec.mscm.security.utils.UserUtils;
import fortec.mscm.settlement.consts.DictConsts;
import fortec.mscm.settlement.entity.Bill;
import fortec.mscm.settlement.entity.BillItem;
import fortec.mscm.settlement.mapper.BillMapper;
import fortec.mscm.settlement.request.BillQueryRequest;
import fortec.mscm.settlement.service.BillItemService;
import fortec.mscm.settlement.service.BillService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
* 记账单 service 实现
*
* @author HL
* @version 1.0
*/
@Slf4j
@AllArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class BillServiceImpl extends BaseServiceImpl<BillMapper, Bill> implements BillService {

    private final BillItemService billItemService;

    @Override
    public boolean removeCascadeById(Serializable id) {
        billItemService.remove(Wrappers.<BillItem>query().eq("bill_id", id));
        return super.removeById(id);
    }

    @Override
    public List<Bill> list(BillQueryRequest request) {
        List<Bill> list = this.list(Wrappers.<Bill>query()
                .eq(request.getStatus() != null, "status", request.getStatus())
                .between(request.getBeginGmtCreate() != null && request.getEndGmtCreate() != null, "gmt_create", request.getBeginGmtCreate(), request.getEndGmtCreate())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<Bill> page(BillQueryRequest request) {
        request.setHospitalId(UserUtils.getHospitalId());
        IPage page = this.page(request.getPage(), Wrappers.<Bill>query()
                .eq("hospital_id",request.getHospitalId())
                .eq(request.getStatus() != null, "status", request.getStatus())
                .between(request.getBeginGmtCreate() != null && request.getEndGmtCreate() != null, "gmt_create", request.getBeginGmtCreate(), request.getEndGmtCreate())
            .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public boolean add(Bill entity) {
        //默认值
        entity.setHospitalId(UserUtils.getHospitalId())
                .setHospitalName(UserUtils.getHospital().getName())
                .setCode(SerialUtils.generateCode(SerialRuleConsts.SETTLEMENT_BILL_CODE))
                .setTotalAmount(0.0)
                .setStatus(DictConsts.BILL_STATUS_RECEIVED)
        ;
        return saveOrUpdate(entity);
    }

    @Override
    public IPage<Bill> pageForSupplier(BillQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        IPage page = this.page(request.getPage(), Wrappers.<Bill>query()
                .eq("supplier_id",request.getSupplierId())
                .eq(request.getStatus() != null, "status", request.getStatus())
                .between(request.getBeginGmtCreate() != null && request.getEndGmtCreate() != null, "gmt_create", request.getBeginGmtCreate(), request.getEndGmtCreate())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

}
    