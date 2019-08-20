package fortec.mscm.base.controller;

import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.feign.api.MaterialFeignApi;
import fortec.mscm.base.feign.vo.MaterialVO;
import fortec.mscm.base.service.MaterialService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MaterialFeignController
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/13 17:25
 */
@Slf4j
@AllArgsConstructor
@RestController
public class MaterialFeignController extends BaseController implements MaterialFeignApi {

    private MaterialService materialService;

    @Override
    public MaterialVO findById(String id) {
        Material material = materialService.getById(id);

        if (material == null){
            return null;
        }

        MaterialVO vo = new MaterialVO();
        BeanUtils.copyProperties(material,vo);
        return vo;
    }
}
