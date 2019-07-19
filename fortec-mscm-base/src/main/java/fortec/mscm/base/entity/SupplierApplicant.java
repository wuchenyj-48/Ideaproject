

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
* SupplierApplicant实体对象
* @author chenchen
*/
@TableName("supplier_applicant")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class SupplierApplicant extends DataEntity  implements Serializable {

    /** 医院ID */
    @NotBlank(message="医院ID不能为空")
    @Length(min=1, max=20, message="医院ID长度必须介于 1 和 20 之间")
    private String hospitalId;

    /** 供应商ID */

    private String supplierId;

    /** 单据号 */

    private String code;

    /** 说明 */
    @Length(max=200, message="说明长度必须介于 0 和 200 之间")
    private String remark;

    /** 单据状态 */

    private Integer status;

    /** 审核时间 */
    private java.util.Date gmtAudited;

    /** 审核人 */
    @Length(max=20, message="审核人长度必须介于 0 和 20 之间")
    private String auditor;

    /** 审核备注 */
    @Length(max=200, message="审核备注长度必须介于 0 和 200 之间")
    private String auditedRemark;

    @TableField(exist = false)
    private String hospitalName;

    @TableField(exist = false)
    private String supplierName;

    public static final int STATUS_UNSUBMIT=0;
    public static final int STATUS_SUBMITED=1;
    public static final int STATUS_PASSED=2;
    public static final int STATUS_CANCELED=3;


}
    