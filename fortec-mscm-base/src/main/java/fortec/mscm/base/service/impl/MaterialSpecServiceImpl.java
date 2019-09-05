
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.MaterialSpec;
import fortec.mscm.base.mapper.MaterialSpecMapper;
import fortec.mscm.base.request.MaterialSpecQueryRequest;
import fortec.mscm.base.service.MaterialSpecService;
import fortec.mscm.security.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* 商品规格 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class MaterialSpecServiceImpl extends BaseServiceImpl<MaterialSpecMapper, MaterialSpec> implements MaterialSpecService {


    @Override
    public IPage<MaterialSpec> pageByKeywords(MaterialSpecQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.pageByKeywords(request.getPage(),request);
    }

    @Override
    public IPage<MaterialSpec> page(MaterialSpecQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<MaterialSpec>query()
                .like(StringUtils.isNotBlank(request.getMaterialSpec()), "material_spec", request.getMaterialSpec())
                .between(request.getBeginPrice() != null && request.getEndPrice() != null, "price", request.getBeginPrice(), request.getEndPrice())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public List<MaterialSpec> list(MaterialSpecQueryRequest request) {
        List<MaterialSpec> list = this.list(Wrappers.<MaterialSpec>query()
                .and(StringUtils.isNotBlank(request.getMaterialId()), q -> q.eq("material_id", request.getMaterialId()))
                .like(StringUtils.isNotBlank(request.getMaterialSpec()), "material_spec", request.getMaterialSpec())
                .between(request.getBeginPrice() != null && request.getEndPrice() != null, "price", request.getBeginPrice(), request.getEndPrice())
                .orderByDesc("gmt_modified")
        );
        return list;
    }
}
    