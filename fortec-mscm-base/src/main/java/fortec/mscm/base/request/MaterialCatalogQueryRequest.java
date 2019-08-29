
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MaterialCatalogQueryRequest extends PageRequest {

    /** 商品类型 : 1：药品，2：耗材，3：试剂。 字典类型：base_material_type */
    private Integer materialTypeCode;

    /** 品类代码 */
    private String code;

    /** 品类名称 */
    private String name;

    /** 上级id */
    private String parentId;


}
    