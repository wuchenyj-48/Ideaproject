package fortec.mscm.base.controller;

import fortec.common.core.mvc.controller.BaseController;
import fortec.mscm.base.entity.Supplier;
import fortec.mscm.base.feign.api.SupplierFeignApi;
import fortec.mscm.base.feign.vo.SupplierVO;
import fortec.mscm.base.service.SupplierService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 供应商 feign 接口实现
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/7 9:49
 * @Version: 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class SupplierFeignController extends BaseController implements SupplierFeignApi {

    private final SupplierService supplierService;

    @Override
    public SupplierVO findByOfficeId(String officeId) {
        Supplier supplier = supplierService.findByOfficeId(officeId);
        if(supplier == null){
            return null;
        }
        SupplierVO vo = new SupplierVO();
        BeanUtils.copyProperties(supplier, vo);

        return vo;
    }
}
