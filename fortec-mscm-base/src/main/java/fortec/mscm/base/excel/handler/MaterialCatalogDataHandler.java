package fortec.mscm.base.excel.handler;/**
 * @Description:
 * @Author: chen.chen
 * @CreateDate: 2019/11/1 16:10
 * @Version: 1.0
 */

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import fortec.common.core.utils.Reflections;
import fortec.common.core.utils.SpringHolder;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.MaterialCatalog;
import fortec.mscm.base.service.MaterialCatalogService;

import java.util.List;
import java.util.Map;

/**
 *@ClassName MaterialCatalogDataHandler
 *@Description TODO
 *@Author Chenchen
 *@Date 2019/11/1 16:10
 */
public class MaterialCatalogDataHandler extends ExcelDataHandlerDefaultImpl {
    private Map<String, MaterialCatalog> codeMap = Maps.newHashMap();

    public MaterialCatalogDataHandler() {
        List<MaterialCatalog> materialCatalogs = this.getMaterialCatalogService().list(Wrappers.<MaterialCatalog>query().select(new String[]{"id,code"}));
        for (MaterialCatalog materialCatalog : materialCatalogs) {
            this.codeMap.put(materialCatalog.getCode(), materialCatalog);
        }

    }

    public String[] getNeedHandlerFields() {
        return new String[]{"品类代码"};
    }

    public Object importHandler(Object obj, String name, Object value) {
        if (!"品类代码".equalsIgnoreCase(name)) {
            return null;
        } else if (value != null && !StringUtils.isBlank(value.toString())) {
            MaterialCatalog materialCatalog = (MaterialCatalog)this.codeMap.get(value.toString());
            if (materialCatalog != null && Reflections.getAccessibleField(obj, "catalogId") != null) {
                Reflections.setFieldValue(obj, "catalogId", materialCatalog.getId());
            }

            return null;
        } else {
            return null;
        }
    }

    public MaterialCatalogService getMaterialCatalogService() {
        return (MaterialCatalogService) SpringHolder.getBean(MaterialCatalogService.class);
    }
}
