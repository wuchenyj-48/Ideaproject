

package fortec.mscm.base.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.TreeEntity;
import fortec.common.core.utils.excel.annotation.ExcelField;
import fortec.common.core.utils.excel.consts.FieldAlign;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* 商品品类实体对象
* @author chenchen
*/
@TableName("material_catalog")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class MaterialCatalogVO extends TreeEntity  implements Serializable {

    /** 商品类型 : 1：药品，2：耗材，3：试剂。 字典类型：base_material_type */
    @ExcelField(title = "商品类型",align = FieldAlign.CENTER, sort = 10,dictType = "base_material_type")
    @NotNull(message="商品类型不能为空")
    private Integer materialTypeCode;

    /** 品类代码 */
    @ExcelField(title = "品类代码",align = FieldAlign.CENTER, sort = 20)
    @NotBlank(message="品类代码不能为空")
    @Length(min=1, max=20, message="品类代码长度必须介于 1 和 20 之间")
    private String code;

    /** 品类名称 */
    @ExcelField(title = "品类名称",align = FieldAlign.CENTER, sort = 30)
    @NotBlank(message="品类名称不能为空")
    @Length(min=1, max=20, message="品类名称长度必须介于 1 和 20 之间")
    private String name;

    /** 品类全称 */
    @ExcelField(title = "品类全称",align = FieldAlign.CENTER, sort = 40)
    @NotBlank(message="品类全称不能为空")
    @Length(min=1, max=50, message="品类全称长度必须介于 1 和 50 之间")
    private String fullName;

    /** 父级品类代码 */
    @ExcelField(title = "父级品类代码",align = FieldAlign.CENTER, sort = 50)
    private String parentCode;

    /** 父级品类名称 */
    @ExcelField(title = "父级品类名称",align = FieldAlign.CENTER, sort = 60)
    private String parentName;

}
    