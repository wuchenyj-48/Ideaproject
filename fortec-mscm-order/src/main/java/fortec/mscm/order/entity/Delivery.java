

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
* 发货单实体对象
* @author Yangjy
*/
@TableName("delivery")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Delivery extends DataEntity  implements Serializable {

    /** 院方ID */
    private Long hospitalId;

    /** 院方名称 */
    private String hospitalName;

    /** 发货单号 */
    private String code;

    /** 订单ID */
    @NotNull(message="订单ID不能为空")
    private Long poId;

    /** 采购单号 */
    @NotBlank(message="采购单号不能为空")
    @Length(min=1, max=50, message="采购单号长度必须介于 1 和 50 之间")
    private String poCode;

    /** 供应商ID */
    private Long supplierId;

    /** 供应商名称 */
    private String supplierName;

    /** 最晚送达日期 */
    private java.util.Date latestDeliverDate;

    /** 订单金额(元) */
    @NotNull(message="订单金额(元)不能为空")
    private Double orderAmount;

    /** 发货金额(元) */
    private Double deliveryAmount;

    /** 发货时间 */
    private java.util.Date gmtDelivery;

    /** 状态 */
    private Integer status;

}
    