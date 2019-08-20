package fortec.mscm.base.controller;

import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.MaterialCatalog;
import fortec.mscm.base.feign.api.CatalogFeignApi;
import fortec.mscm.base.feign.vo.CatalogVO;
import fortec.mscm.base.service.MaterialCatalogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CatalogFignController
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/13 17:23
 */
@Slf4j
@AllArgsConstructor
@RestController
public class CatalogFignController extends BaseController implements CatalogFeignApi {

    private MaterialCatalogService materialCatalogService;

    @Override
    public CatalogVO findById(String id) {
        MaterialCatalog catalog = materialCatalogService.getById(id);

        if (catalog == null){
            return null;
        }

        CatalogVO vo = new CatalogVO();
        BeanUtils.copyProperties(catalog,vo);
        return vo;
    }
}
