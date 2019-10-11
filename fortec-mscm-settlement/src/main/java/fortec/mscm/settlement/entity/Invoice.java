

package fortec.mscm.settlement.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
* 开票单实体对象
* @author chenchen
*/
@TableName("invoice")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Invoice extends DataEntity  implements Serializable {

    /** 单号 */
    private String code;

    /** 院方ID */
    @NotBlank(message="院方ID不能为空")
    @Length(min=1, max=20, message="院方ID长度必须介于 1 和 20 之间")
    private String hospitalId;

    /** 院方名称 */
    @NotBlank(message="院方名称不能为空")
    @Length(min=1, max=50, message="院方名称长度必须介于 1 和 50 之间")
    private String hospitalName;

    /** 供应商ID */
    private String supplierId;

    /** 供应商名称 */
    private String supplierName;

    /** 开票时间 */
    @NotNull(message="开票时间不能为空")
    private java.util.Date gmtMakeOut;

    /** 开票人 */
    @NotBlank(message="开票人不能为空")
    @Length(min=1, max=50, message="开票人长度必须介于 1 和 50 之间")
    private String drawer;

    /** 开票金额 */
    @NotNull(message="开票金额不能为空")
    private Double totalAmount;

    /** 备注 */
    @Length(max=100, message="备注长度必须介于 0 和 100 之间")
    private String remark;

}
    