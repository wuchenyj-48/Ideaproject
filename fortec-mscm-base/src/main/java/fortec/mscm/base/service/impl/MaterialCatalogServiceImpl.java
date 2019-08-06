
package fortec.mscm.base.service.impl;

import fortec.common.core.service.TreeServiceImpl;

import fortec.mscm.base.entity.MaterialCatalog;
import fortec.mscm.base.mapper.MaterialCatalogMapper;
import fortec.mscm.base.request.MaterialCatalogQueryRequest;
import fortec.mscm.base.service.MaterialCatalogService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* 商品品类 service 实现
*
* @author chenchen
* @version 1.0
*/
@Slf4j
@Service
public class MaterialCatalogServiceImpl extends TreeServiceImpl<MaterialCatalogMapper, MaterialCatalog> implements MaterialCatalogService {


}
    