package fortec.mscm.base.feign.api;

import fortec.mscm.base.feign.vo.CatalogVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CatalogFeignApi {

    /**
     * 根据品类id获取品类信息
     * @param id
     * @return
     */
    @GetMapping("/feign/material_catalogs/{id}")
    CatalogVO findById(@PathVariable("id") String id);
}
