
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.base.entity.MaterialSpec;
import fortec.mscm.base.mapper.MaterialSpecMapper;
import fortec.mscm.base.request.MaterialSpecQueryRequest;
import fortec.mscm.base.service.MaterialSpecService;
import fortec.mscm.base.vo.MaterialSpecVO;
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
    public IPage<MaterialSpecVO> page(MaterialSpecQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        IPage page = baseMapper.page(request.getPage(),request);
        return page;
    }

    @Override
    public List<MaterialSpecVO> list(MaterialSpecQueryRequest request) {
        return this.baseMapper.list(request);
    }

}
    