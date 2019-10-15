
package fortec.mscm.settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.security.utils.UserUtils;
import fortec.mscm.settlement.entity.Stock;
import fortec.mscm.settlement.mapper.StockMapper;
import fortec.mscm.settlement.request.StockQueryRequest;
import fortec.mscm.settlement.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* 库存管理 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StockServiceImpl extends BaseServiceImpl<StockMapper, Stock> implements StockService {

    @Override
    public List<Stock> list(StockQueryRequest request) {
        List<Stock> list = this.list(Wrappers.<Stock>query()
                .like(StringUtils.isNotBlank(request.getHospitalName()), "hospital_name", request.getHospitalName())
                .like(StringUtils.isNotBlank(request.getSupplierName()), "supplier_name", request.getSupplierName())
                .like(StringUtils.isNotBlank(request.getWarehouseName()), "warehouse_name", request.getWarehouseName())
                .like(StringUtils.isNotBlank(request.getMaterialName()), "material_name", request.getMaterialName())
                .like(StringUtils.isNotBlank(request.getMaterialSpec()), "material_spec", request.getMaterialSpec())
                .between(request.getBeginPrice() != null && request.getEndPrice() != null, "price", request.getBeginPrice(), request.getEndPrice())
                .between(request.getBeginExpiredDate() != null && request.getEndExpiredDate() != null, "expired_date", request.getBeginExpiredDate(), request.getEndExpiredDate())
            .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<Stock> page(StockQueryRequest request) {
        request.setHospitalId(UserUtils.getHospitalId());
        IPage page = this.page(request.getPage(), Wrappers.<Stock>query()
                .eq(StringUtils.isNotBlank(request.getHospitalId()), "hospital_id", request.getHospitalId())
                .like(StringUtils.isNotBlank(request.getSupplierName()), "supplier_name", request.getSupplierName())
                .like(StringUtils.isNotBlank(request.getWarehouseName()), "warehouse_name", request.getWarehouseName())
                .like(StringUtils.isNotBlank(request.getMaterialName()), "material_name", request.getMaterialName())
                .like(StringUtils.isNotBlank(request.getMaterialSpec()), "material_spec", request.getMaterialSpec())
                .between(request.getBeginPrice() != null && request.getEndPrice() != null, "price", request.getBeginPrice(), request.getEndPrice())
                .between(request.getBeginExpiredDate() != null && request.getEndExpiredDate() != null, "expired_date", request.getBeginExpiredDate(), request.getEndExpiredDate())
            .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public IPage<Stock> pageForSupplier(StockQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        IPage page = this.page(request.getPage(), Wrappers.<Stock>query()
                .eq(StringUtils.isNotBlank(request.getSupplierId()), "supplier_id", request.getSupplierId())
                .like(StringUtils.isNotBlank(request.getHospitalName()), "hospital_name", request.getHospitalName())
                .like(StringUtils.isNotBlank(request.getWarehouseName()), "warehouse_name", request.getWarehouseName())
                .like(StringUtils.isNotBlank(request.getMaterialName()), "material_name", request.getMaterialName())
                .like(StringUtils.isNotBlank(request.getMaterialSpec()), "material_spec", request.getMaterialSpec())
                .between(request.getBeginPrice() != null && request.getEndPrice() != null, "price", request.getBeginPrice(), request.getEndPrice())
                .between(request.getBeginExpiredDate() != null && request.getEndExpiredDate() != null, "expired_date", request.getBeginExpiredDate(), request.getEndExpiredDate())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public boolean saveCascadeById(Stock entity) {
        entity.setHospitalId(UserUtils.getHospitalId())
                .setHospitalName(UserUtils.getHospital().getName());
        return super.saveCascadeById(entity);
    }
}
    