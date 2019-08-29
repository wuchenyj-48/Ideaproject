

package fortec.mscm.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.TreeEntity;
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
public class MaterialCatalog extends TreeEntity  implements Serializable {

    /** 商品类型 : 1：药品，2：耗材，3：试剂。 字典类型：base_material_type */
    @NotNull(message="商品类型 : 1：药品，2：耗材，3：试剂。 字典类型：base_material_type不能为空")
    private Integer materialTypeCode;

    /** 品类代码 */
    @NotBlank(message="品类代码不能为空")
    @Length(min=1, max=20, message="品类代码长度必须介于 1 和 20 之间")
    private String code;

    /** 品类名称 */
    @NotBlank(message="品类名称不能为空")
    @Length(min=1, max=20, message="品类名称长度必须介于 1 和 20 之间")
    private String name;

    /** 品类全称 */
    @NotBlank(message="品类全称不能为空")
    @Length(min=1, max=50, message="品类全称长度必须介于 1 和 50 之间")
    private String fullName;

    /** 父级品类名称 */
    @TableField(exist = false)
    private String parentName;

}
    