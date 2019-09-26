
package fortec.mscm.settlement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.settlement.entity.BillItem;
import fortec.mscm.settlement.request.BillItemQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 记账单明细 service 接口
*
* @author hl
* @version 1.0
*/
public interface BillItemService extends IBaseService<BillItem> {

    List<BillItem> list(BillItemQueryRequest request);


    IPage<BillItem> page(BillItemQueryRequest request);

    /**
     * 添加一行明细
     * @param entity
     */
    void add(BillItem entity);

    /**
     * 批量添加明细
     * @param id
     */
    void delete(String id);

    /**
     * 删除一行明细
     * @param children
     */
    void batchSave(BillItem[] children);

    /**
     * 获取记账金额
     * @param billId
     * @return
     */
    Double totalAmount(String billId);

}
    