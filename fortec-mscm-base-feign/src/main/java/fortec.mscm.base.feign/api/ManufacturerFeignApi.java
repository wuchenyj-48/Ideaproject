package fortec.mscm.base.feign.api;

import fortec.mscm.base.feign.vo.ManufacturerVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 厂商Feign API接口
 */
public interface ManufacturerFeignApi {

    /**
     * 根据厂商id获取厂商信息
     * @param id
     * @return
     */
    @GetMapping("/feign/manufacturers/{id}")
    ManufacturerVO findById(@PathVariable("id") String id);
}
