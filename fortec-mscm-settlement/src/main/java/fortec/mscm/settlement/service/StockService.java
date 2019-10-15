
package fortec.mscm.settlement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.settlement.entity.Stock;
import fortec.mscm.settlement.request.StockQueryRequest;

import fortec.common.core.service.IBaseService;

import java.util.List;

/**
* 库存管理 service 接口
*
* @author chenchen
* @version 1.0
*/
public interface StockService extends IBaseService<Stock> {

    List<Stock> list(StockQueryRequest request);


    IPage<Stock> page(StockQueryRequest request);

    IPage<Stock> pageForSupplier(StockQueryRequest request);

}
    