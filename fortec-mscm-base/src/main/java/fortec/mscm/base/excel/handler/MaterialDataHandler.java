package fortec.mscm.base.excel.handler;/**
 * @Description:
 * @Author: chen.chen
 * @CreateDate: 2019/11/1 16:50
 * @Version: 1.0
 */

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import fortec.common.core.utils.Reflections;
import fortec.common.core.utils.SpringHolder;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.service.MaterialService;
import fortec.mscm.security.utils.UserUtils;

import java.util.List;
import java.util.Map;

/**
 *@ClassName MaterialDataHandler
 *@Description TODO
 *@Author Chenchen
 *@Date 2019/11/1 16:50
 */
public class MaterialDataHandler  extends ExcelDataHandlerDefaultImpl {
    private Map<String, Material> codeMap = Maps.newHashMap();

    public MaterialDataHandler() {
        List<Material> materials = this.getMaterialService().list(
                Wrappers.<Material>query().select(new String[]{"id,material_erp_code"}).eq("supplier_id", UserUtils.getSupplierId())
        );
        for (Material material : materials) {
            this.codeMap.put(material.getMaterialErpCode(), material);
        }

    }

    public String[] getNeedHandlerFields() {
        return new String[]{"ERP代码"};
    }

    public Object importHandler(Object obj, String name, Object value) {
        if (!"ERP代码".equalsIgnoreCase(name)) {
            return null;
        }
        if (value != null && !StringUtils.isBlank(value.toString())) {
            Material material = this.codeMap.get(value.toString());
            if (material != null && Reflections.getAccessibleField(obj, "id") != null) {
                Reflections.setFieldValue(obj, "id", material.getId());
            }

            return null;
        }

            return null;

    }

    public MaterialService getMaterialService() {
        return SpringHolder.getBean(MaterialService.class);
    }
}
