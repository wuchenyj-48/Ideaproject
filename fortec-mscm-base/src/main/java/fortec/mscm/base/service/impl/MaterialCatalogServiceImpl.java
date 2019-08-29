
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.service.TreeServiceImpl;
import fortec.mscm.base.entity.MaterialCatalog;
import fortec.mscm.base.mapper.MaterialCatalogMapper;
import fortec.mscm.base.request.MaterialCatalogQueryRequest;
import fortec.mscm.base.service.MaterialCatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
* 商品品类 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class MaterialCatalogServiceImpl extends TreeServiceImpl<MaterialCatalogMapper, MaterialCatalog> implements MaterialCatalogService {


    @Override
    public boolean deleteById(String id) {
        //是否有下属商品
        int count = this.count(Wrappers.<MaterialCatalog>query().eq("parent_id", id));
        if (count>0){
            throw new BusinessException("已经有下属商品的品类不能删除");
        }
        return this.removeCascadeById(id);
    }

    @Override
    public IPage pageForTree(MaterialCatalogQueryRequest request) {
        return this.baseMapper.pageForTree(request.getPage(),request);
    }


}
    