package fortec.mscm.base.controller;

import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.Manufacturer;
import fortec.mscm.base.feign.api.ManufacturerFeignApi;
import fortec.mscm.base.feign.vo.ManufacturerVO;
import fortec.mscm.base.service.ManufacturerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ManufacturerFeighController
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/13 15:41
 */
@Slf4j
@AllArgsConstructor
@RestController
public class ManufacturerFeignController extends BaseController implements ManufacturerFeignApi {

    private final ManufacturerService manufacturerService;

    @Override
    public ManufacturerVO findById(String id) {

        Manufacturer manufacturer = manufacturerService.getById(id);

        if (manufacturer == null){
            return null;
        }

        ManufacturerVO vo = new ManufacturerVO();
        BeanUtils.copyProperties(manufacturer,vo);
        return vo;
    }
}
