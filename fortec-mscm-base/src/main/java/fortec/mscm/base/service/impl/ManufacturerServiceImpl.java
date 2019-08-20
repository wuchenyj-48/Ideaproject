
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.Manufacturer;
import fortec.mscm.base.mapper.ManufacturerMapper;
import fortec.mscm.base.service.ManufacturerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 厂商 service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@Slf4j
@Service
public class ManufacturerServiceImpl extends BaseServiceImpl<ManufacturerMapper, Manufacturer> implements ManufacturerService {

    /**
     * 同一供应商下，社会信用代码唯一
     *
     * @param entity
     * @return
     */
    @Override
    public boolean add(Manufacturer entity) {
        int count = this.count(Wrappers.<Manufacturer>query()
                .eq("supplier_id", entity.getSupplierId())
                .eq("company_code", entity.getCompanyCode())
        );
        return count > 0 ? false : this.saveCascadeById(entity);

    }

    @Override
    public boolean update(Manufacturer entity) {
        int count = this.count(Wrappers.<Manufacturer>query()
                .eq("supplier_id", entity.getSupplierId())
                .eq("company_code", entity.getCompanyCode())
                .ne("id", entity.getId())
        );
        return count > 0 ? false : this.updateCascadeById(entity);
    }

    /**
     * 同一供应商下，社会信用代码唯一
     *
     * @param entityList
     * @return
     */
    @Override
    public boolean saveOrUpdateBatch(Collection<Manufacturer> entityList) {
        if (entityList.size() == 0) {
            return false;
        }
        Set<String> set = entityList.stream().map(o -> o.getCompanyCode()).collect(Collectors.toSet());
        if (set.size() < entityList.size()) {
            throw new BusinessException("存在重复的社会信用代码");
        }

        List<String> existCodeList = this.list(Wrappers.<Manufacturer>query()
                .eq("supplier_id", entityList.stream().findFirst().get().getSupplierId())
        ).stream().map(o -> o.getCompanyCode()).collect(Collectors.toList());
        for (Manufacturer manufacturer : entityList) {
            boolean contains = false;
            if (StringUtils.isBlank(manufacturer.getId())) {
                // 新增
                contains = existCodeList.contains(manufacturer.getCompanyCode());
            } else {
                contains = existCodeList.stream().filter(o -> o.equals(manufacturer.getCompanyCode())).count() > 1;
            }
            if (contains) {
                throw new BusinessException("存在重复的社会信用代码" + manufacturer.getCompanyCode());
            }
        }

        return super.saveOrUpdateBatch(entityList);
    }
}
    