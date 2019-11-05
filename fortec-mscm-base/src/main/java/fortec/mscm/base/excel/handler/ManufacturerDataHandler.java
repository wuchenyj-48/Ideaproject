package fortec.mscm.base.excel.handler;/**
 * @Description:
 * @Author: chen.chen
 * @CreateDate: 2019/11/1 15:58
 * @Version: 1.0
 */

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import fortec.common.core.utils.Reflections;
import fortec.common.core.utils.SpringHolder;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.Manufacturer;
import fortec.mscm.base.service.ManufacturerService;

import java.util.List;
import java.util.Map;

/**
 *@ClassName ManufacturerDataHandler
 *@Description TODO
 *@Author Chenchen
 *@Date 2019/11/1 15:58
 */
public class ManufacturerDataHandler extends ExcelDataHandlerDefaultImpl {
    private Map<String, Manufacturer> companyCodeMap = Maps.newHashMap();

    public ManufacturerDataHandler() {
        List<Manufacturer> manufacturers = this.getManufacturerService().list(Wrappers.<Manufacturer>query().select(new String[]{"id,company_code"}));

        for (Manufacturer manufacturer : manufacturers) {
            this.companyCodeMap.put(manufacturer.getCompanyCode(), manufacturer);
        }
    }

    public String[] getNeedHandlerFields() {
        return new String[]{"厂商代码"};
    }

    public Object importHandler(Object obj, String name, Object value) {
        if (!"厂商代码".equalsIgnoreCase(name)) {
            return null;
        } else if (value != null && !StringUtils.isBlank(value.toString())) {
            Manufacturer manufacturer = (Manufacturer)this.companyCodeMap.get(value.toString());
            if (manufacturer != null && Reflections.getAccessibleField(obj, "manufacturerId") != null) {
                Reflections.setFieldValue(obj, "manufacturerId", manufacturer.getId());
            }

            return null;
        } else {
            return null;
        }
    }

    public ManufacturerService getManufacturerService() {
        return (ManufacturerService) SpringHolder.getBean(ManufacturerService.class);
    }
}
