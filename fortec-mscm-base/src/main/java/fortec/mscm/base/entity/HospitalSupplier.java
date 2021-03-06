

package fortec.mscm.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
/**
* HospitalSupplier实体对象
* @author chenchen
*/
@TableName("hospital_supplier")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class HospitalSupplier extends DataEntity  implements Serializable {

    /** 医院ID */
    @NotBlank(message="医院ID不能为空")
    @Length(min=1, max=20, message="医院ID长度必须介于 1 和 20 之间")
    private String hospitalId;

    /** 供应商ID */
    @NotBlank(message="供应商ID不能为空")
    @Length(min=1, max=20, message="供应商ID长度必须介于 1 和 20 之间")
    private String supplierId;

    private Integer inactive;

    /** 医院名称 */
    @TableField(exist = false)
    private String hospitalName;

    /** 医院代码 */
    @TableField(exist = false)
    private String code;

    /** 供应商名称 */
    @TableField(exist = false)
    private String supplierName;

    public static final int ENABLE = 0;
    public static final int DISABLE = 1;

}
    