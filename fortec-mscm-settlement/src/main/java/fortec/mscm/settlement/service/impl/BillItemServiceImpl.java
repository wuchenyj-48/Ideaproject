
package fortec.mscm.settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.settlement.entity.Bill;
import fortec.mscm.settlement.entity.BillItem;
import fortec.mscm.settlement.mapper.BillItemMapper;
import fortec.mscm.settlement.request.BillItemQueryRequest;
import fortec.mscm.settlement.service.BillItemService;
import fortec.mscm.settlement.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* 记账单明细 service 实现
*
* @author hl
* @version 1.0
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BillItemServiceImpl extends BaseServiceImpl<BillItemMapper, BillItem> implements BillItemService {
    @Autowired
    private BillService billService;

    @Override
    public List<BillItem> list(BillItemQueryRequest request) {
        List<BillItem> list = this.list(Wrappers.<BillItem>query()
                .eq(StringUtils.isNotBlank(request.getBillId()), "bill_id", request.getBillId())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<BillItem> page(BillItemQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<BillItem>query()
                .eq(StringUtils.isNotBlank(request.getBillId()), "bill_id", request.getBillId())
            .orderByDesc("gmt_modified")
        );
        return page;
    }


    @Override
    public void add(BillItem entity) {
        saveOrUpdate(entity);

        //修改记账金额
        Double totalAmount = totalAmount(entity.getBillId());
        Bill bill = new Bill();
        bill.setTotalAmount(totalAmount).setId(entity.getBillId());
        billService.updateById(bill);
    }

    @Override
    public void delete(String id) {
        BillItem byId = getById(id);
        if (byId == null){
            throw new BusinessException("数据异常");
        }
        removeCascadeById(id);

        //修改记账金额
        Double totalAmount = totalAmount(byId.getBillId());
        Bill bill = new Bill();
        bill.setTotalAmount(totalAmount).setId(byId.getBillId());
        billService.updateById(bill);
    }

    @Override
    public void batchSave(BillItem[] children) {

        saveOrUpdateBatch(Lists.newArrayList(children));

        //修改记账金额
        Double totalAmount = totalAmount(children[0].getBillId());
        Bill bill = new Bill();
        bill.setTotalAmount(totalAmount).setId(children[0].getBillId());
        billService.updateById(bill);
    }

    @Override
    public Double totalAmount(String billId) {
        Double totalAmount = baseMapper.totalAmount(billId);
        return totalAmount == null ? 0 : totalAmount;
    }
}
    