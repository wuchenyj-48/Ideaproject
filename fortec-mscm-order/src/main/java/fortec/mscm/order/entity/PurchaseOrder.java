

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
* 采购订单实体对象
* @author chenchen
*/
@TableName("purchase_order")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PurchaseOrder extends DataEntity  implements Serializable {

    /** 院方ID */
    @NotBlank(message="院方ID不能为空")
    @Length(min=1, max=20, message="院方ID长度必须介于 1 和 20 之间")
    private String hospitalId;

    /** 院方名称 */
    @NotBlank(message="院方名称不能为空")
    @Length(min=1, max=50, message="院方名称长度必须介于 1 和 50 之间")
    private String hospitalName;

    /** 订单编号 */
    @Length(min=1, max=50, message="订单编号长度必须介于 1 和 50 之间")
    private String code;

    /** 供应商ID */
    @NotNull(message="供应商ID不能为空")
    private Long supplierId;

    /** 供应商名称 */
    @NotBlank(message="供应商名称不能为空")
    @Length(min=1, max=50, message="供应商名称长度必须介于 1 和 50 之间")
    private String supplierName;

    /** 送达地点ID */
    @NotNull(message="送达地点ID不能为空")
    private Long warehouseId;

    /** 送达地点名称 */
    @NotBlank(message="送达地点名称不能为空")
    @Length(min=1, max=50, message="送达地点名称长度必须介于 1 和 50 之间")
    private String warehouseName;

    /** 是否寄售 */
    @NotNull(message="是否寄售不能为空")
    private Integer isConsignment;

    /** 是否一物一码*/
    @NotNull(message="是否一物一码不能为空")
    private Integer isOneThingOneYard;

    /** 最晚送达日期 */
    @NotNull(message="最晚送达日期不能为空")
    private java.util.Date latestDeliveryDate;

    /** 订单金额(元) */
    @NotNull(message="订单金额(元)不能为空")
    private Double totalAmount;

    /** 采购员 */
    @NotNull(message="采购员不能为空")
    private Long buyerName;

    /** 审核人 */
    private Long auditorName;

    /** 审核时间 */
    private java.util.Date gmtAudited;

    /** 供应商确认状态  */
    @NotNull(message="供应商确认状态 不能为空")
    private Integer supplierConfirmStatus;

    /** 供应商确认用户 */
    private Long supplierConfirmer;

    /** 供应商确认时间 */
    private java.util.Date gmtSupplierConfirmed;

    /** 发货状态 */
    @NotNull(message="发货状态不能为空")
    private Integer deliveryStatus;

    /** 首次发货时间 */
    private java.util.Date gmtFirstDelivery;

    /** 订单状态  */
    @NotNull(message="订单状态 不能为空")
    private Integer status;

    /** 是否关闭 */
    @NotNull(message="是否关闭不能为空")
    private Integer isClosed;

    /** 关闭时间 */
    private java.util.Date gmtClosed;

    /** 数据来源 */
    private Integer source;

}
    