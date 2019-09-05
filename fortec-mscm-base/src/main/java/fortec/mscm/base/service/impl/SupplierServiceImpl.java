
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.Supplier;
import fortec.mscm.base.mapper.SupplierMapper;
import fortec.mscm.base.request.SupplierQueryRequest;
import fortec.mscm.base.service.SupplierService;
import fortec.mscm.security.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    public IPage<Supplier> page(SupplierQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Supplier>query()
                .like(StringUtils.isNotBlank(request.getCompanyCode()), "company_code", request.getCompanyCode())
                .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                .eq(request.getIsDrug() != null, "is_drug", request.getIsDrug())
                .eq(request.getIsConsumable() != null, "is_consumable", request.getIsConsumable())
                .eq(request.getIsReagent() != null, "is_reagent", request.getIsReagent())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public IPage<Supplier> pageForSupplier(SupplierQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Supplier>query()
                .eq(StringUtils.isNotBlank(UserUtils.getSupplierId()), "id", UserUtils.getSupplierId())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public List<Supplier> list(SupplierQueryRequest request) {
        List<Supplier> list = this.list(Wrappers.<Supplier>query()
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<Supplier> pageByKeywords(SupplierQueryRequest request,String keywords) {
        IPage page = this.page(request.getPage(), Wrappers.<Supplier>query()
                .like(StringUtils.isNotBlank(keywords), "company_code", keywords)
                .or()
                .like(StringUtils.isNotBlank(keywords), "code", keywords)
                .or()
                .like(StringUtils.isNotBlank(keywords), "name", keywords)
                .orderByDesc("gmt_modified")
        );
        return page;
    }
}
    