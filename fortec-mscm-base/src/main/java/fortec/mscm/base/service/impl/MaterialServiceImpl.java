
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.mapper.MaterialMapper;
import fortec.mscm.base.request.MaterialQueryRequest;
import fortec.mscm.base.service.MaterialService;
import fortec.mscm.security.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* 商品 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class MaterialServiceImpl extends BaseServiceImpl<MaterialMapper, Material> implements MaterialService {

    @Override
    public IPage<Material> page(MaterialQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.page(request.getPage(), request);
    }

    @Override
    public List<Material> list(MaterialQueryRequest request) {
        List<Material> list = this.list(Wrappers.<Material>query()
                .orderByDesc("gmt_modified")
        );
        return list;
    }


    @Override
    public boolean saveOrUpdate(Material entity) {
        entity.setSupplierId(UserUtils.getSupplierId());
        return super.saveOrUpdate(entity);
    }
}
    