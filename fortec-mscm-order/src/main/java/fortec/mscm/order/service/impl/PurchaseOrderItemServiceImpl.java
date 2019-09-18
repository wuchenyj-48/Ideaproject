
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;

import fortec.mscm.order.entity.PurchaseOrderItem;
import fortec.mscm.order.mapper.PurchaseOrderItemMapper;
import fortec.mscm.order.request.PurchaseOrderItemQueryRequest;
import fortec.mscm.order.service.PurchaseOrderItemService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
* 采购订单明细 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class PurchaseOrderItemServiceImpl extends BaseServiceImpl<PurchaseOrderItemMapper, PurchaseOrderItem> implements PurchaseOrderItemService {

    @Override
    public List<PurchaseOrderItem> list(PurchaseOrderItemQueryRequest request) {
        List<PurchaseOrderItem> list = this.list(Wrappers.<PurchaseOrderItem>query()
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<PurchaseOrderItem> page(PurchaseOrderItemQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<PurchaseOrderItem>query()
            .orderByDesc("gmt_modified")
        );
        return page;
    }
}
    