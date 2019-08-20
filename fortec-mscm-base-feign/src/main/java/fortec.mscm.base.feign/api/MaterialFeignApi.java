package fortec.mscm.base.feign.api;

import fortec.mscm.base.feign.vo.MaterialVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface MaterialFeignApi {

    /**
     * 根据商品id获取商品信息
     * @param id
     * @return
     */
    @GetMapping("/feign/materials/{id}")
    MaterialVO findById(@PathVariable("id") String id);
}
