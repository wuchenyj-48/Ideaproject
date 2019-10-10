

package fortec.mscm.settlement.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
* 发票单明细实体对象
* @author chenchen
*/
@TableName("invoice_item")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class InvoiceItem extends DataEntity  implements Serializable {

    /** 开票单ID */
    @NotNull(message="开票单ID不能为空")
    private Long invoiceId;

    /** 开票单行ID */
    @NotNull(message="开票单行ID不能为空")
    private Long invoiceLineId;

    /** 记账单ID */
    @NotNull(message="记账单ID不能为空")
    private Long billId;

    /** 记账单明细ID */
    @NotNull(message="记账单明细ID不能为空")
    private Long billItemId;

    /** 备注 */
    @Length(max=100, message="备注长度必须介于 0 和 100 之间")
    private String remark;

}
    