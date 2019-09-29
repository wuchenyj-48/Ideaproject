
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.order.entity.DeliveryItem;
import fortec.mscm.order.entity.DeliveryItemSn;
import fortec.mscm.order.mapper.DeliveryItemMapper;
import fortec.mscm.order.mapper.DeliveryItemSnMapper;
import fortec.mscm.order.request.DeliveryItemSnQueryRequest;
import fortec.mscm.order.service.DeliveryItemSnService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * sn生成和查询 service 实现
 *
 * @author Yangjianye
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DeliveryItemSnServiceImpl extends BaseServiceImpl<DeliveryItemSnMapper, DeliveryItemSn> implements DeliveryItemSnService {

    private final DeliveryItemMapper deliveryItemMapper;




    @Override
    public List<DeliveryItemSn> list(DeliveryItemSnQueryRequest request) {
        List<DeliveryItemSn> list = this.list(Wrappers.<DeliveryItemSn>query()
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<DeliveryItemSn> page(DeliveryItemSnQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<DeliveryItemSn>query()
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public List<DeliveryItemSn> saveDeliveryItemSns(String deliveryId) {
//        查询订单明细
        List<DeliveryItem> deliveryItems = deliveryItemMapper.selectList(Wrappers.<DeliveryItem>query()
                .eq("delivery_id", deliveryId)
                .orderByDesc("gmt_modified"));
        List<DeliveryItemSn> deliveryItemSnList = new ArrayList<>();
//          查询 订单明细生成的SN
        List<DeliveryItemSn> returnSnList = this.list(Wrappers.<DeliveryItemSn>query()
                .eq("delivery_id", deliveryId)
                .select("sn")
                .orderByDesc("gmt_modified"));

//          如果不存在,新增
        if (returnSnList.size() == 0) {

            for (DeliveryItem deliveryItem : deliveryItems) {

                Double qty = deliveryItem.getQty();
                long qtyItem = Math.round(qty);
                for (long i = 0; i < qtyItem; i++) {
                    DeliveryItemSn deliveryItemSn = new DeliveryItemSn();
                    deliveryItemSn.setSn(StringUtils.getRandomStr(20))
                            .setDeliveryId(deliveryItem.getDeliveryId())
                            .setDeliveryItemId(deliveryItem.getId())
                            .setId(null);
                    deliveryItemSnList.add(deliveryItemSn);
                }
            }
            boolean insertSNBool = this.saveBatch(deliveryItemSnList);
            if (!insertSNBool) {
                throw new BusinessException("生成SN失败,请重试");
            }
            return this.list(Wrappers.<DeliveryItemSn>query()
                    .eq("delivery_id", deliveryId)
                    .select("sn")
                    .orderByDesc("gmt_modified"));
        }
        return returnSnList;
    }
}
    