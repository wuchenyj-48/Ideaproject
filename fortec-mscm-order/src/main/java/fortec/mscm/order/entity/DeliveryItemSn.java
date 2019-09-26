

package fortec.mscm.order.entity;

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
* sn生成和查询实体对象
* @author Yangjianye
*/
@TableName("delivery_item_sn")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeliveryItemSn extends DataEntity  implements Serializable {

    /** 发货单主表ID */
    @NotNull(message="发货单主表ID不能为空")
    private String deliveryId;

    /** 发货单明细ID */
    @NotNull(message="发货单明细ID不能为空")
    private String deliveryItemId;

    /** 序列号 */
    @NotBlank(message="序列号不能为空")
    @Length(min=1, max=20, message="序列号长度必须介于 1 和 20 之间")
    private String sn;

}
    