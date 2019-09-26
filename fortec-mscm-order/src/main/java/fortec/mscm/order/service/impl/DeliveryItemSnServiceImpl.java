
package fortec.mscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
    public boolean saveDeliveryItemSns(String deliveryId) {
        DeliveryItemSn deliveryItemSn = new DeliveryItemSn();
        List<DeliveryItem> deliveryItems = deliveryItemMapper.selectList(Wrappers.<DeliveryItem>query()
                .eq("delivery_id", deliveryId)
                .orderByDesc("gmt_modified"));
        List<DeliveryItemSn> deliveryItemSnList = new ArrayList<>();
        for (DeliveryItem deliveryItem : deliveryItems) {
            deliveryItemSn.setSn(StringUtils.getRandomStr(20))
                    .setDeliveryId(deliveryItem.getDeliveryId())
                    .setDeliveryItemId(deliveryItem.getId())
                    .setId(null);
            deliveryItemSnList.add(deliveryItemSn);

        }

        return this.saveBatch(deliveryItemSnList);
    }
}
    