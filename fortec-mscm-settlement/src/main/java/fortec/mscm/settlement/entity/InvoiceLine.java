

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
* 发票单行信息实体对象
* @author chenchen
*/
@TableName("invoice_line")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class InvoiceLine extends DataEntity  implements Serializable {

    /** 开票单ID */
    @NotBlank(message="开票单ID不能为空")
    @Length(min=1, max=20, message="开票单ID长度必须介于 1 和 20 之间")
    private String invoiceId;

    /** 税务发票号 */
    @NotBlank(message="税务发票号不能为空")
    @Length(min=1, max=20, message="税务发票号长度必须介于 1 和 20 之间")
    private String invoiceNo;

    /** 开票金额 */
    @NotNull(message="开票金额不能为空")
    private Double amount;

    /** 备注 */
    @Length(max=100, message="备注长度必须介于 0 和 100 之间")
    private String remark;

    /** 记账单数量 */
    private int billQty;

}
    