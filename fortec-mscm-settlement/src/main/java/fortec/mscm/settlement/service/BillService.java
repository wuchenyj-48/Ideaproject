
package fortec.mscm.settlement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.settlement.entity.Bill;
import fortec.mscm.settlement.request.BillQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 记账单 service 接口
*
* @author HL
* @version 1.0
*/
public interface BillService extends IBaseService<Bill> {

    List<Bill> list(BillQueryRequest request);


    IPage<Bill> page(BillQueryRequest request);

    boolean add(Bill entity);

    IPage<Bill> pageForSupplier(BillQueryRequest request);

}
    