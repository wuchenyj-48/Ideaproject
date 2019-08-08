
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.base.entity.MaterialSpec;
import fortec.mscm.base.mapper.MaterialSpecMapper;
import fortec.mscm.base.request.MaterialSpecQueryRequest;
import fortec.mscm.base.service.MaterialSpecService;
import fortec.mscm.security.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


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
}
    