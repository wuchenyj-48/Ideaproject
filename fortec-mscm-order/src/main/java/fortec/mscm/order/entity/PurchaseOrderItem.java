

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
* 采购订单明细实体对象
* @author chenchen
*/
@TableName("purchase_order_item")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PurchaseOrderItem extends DataEntity  implements Serializable {

    /** 订单ID */
    @NotNull(message="订单ID不能为空")
    private String poId;

    /** 商品规格ID */
    @NotNull(message="商品规格ID不能为空")
    private Long materialSpecId;

    /** 商品编码 */
    private Long materialCode;

    /** 商品代码 */
    @Length(max=20, message="商品代码长度必须介于 0 和 20 之间")
    private String materialErpCode;

    /** 商品名称 */
    @Length(max=100, message="商品名称长度必须介于 0 和 100 之间")
    private String materialName;

    /** 商品通用名 */
    @Length(max=100, message="商品通用名长度必须介于 0 和 100 之间")
    private String materialTradeName;

    /** 规格 */
    @NotBlank(message="规格不能为空")
    @Length(min=1, max=100, message="规格长度必须介于 1 和 100 之间")
    private String materialSpec;

    /** 厂商名称 */
    @NotBlank(message="厂商名称不能为空")
    @Length(min=1, max=50, message="厂商名称长度必须介于 1 和 50 之间")
    private String manufacturerName;

    /** 剂型 */
    @Length(max=50, message="剂型长度必须介于 0 和 50 之间")
    private String form;

    /** 价格 */
    @NotNull(message="价格不能为空")
    private Double price;

    /** 小计(元) */
    @NotNull(message="小计(元)不能为空")
    private Double subtotalAmount;

    /** 已发货数量 */
    @NotNull(message="已发货数量不能为空")
    private Double deliveredQty;

    /** 已发货金额(元) */
    @NotNull(message="已发货金额(元)不能为空")
    private Double deliveredAmount;

    /** 发货状态 */
    @NotNull(message="发货状态不能为空")
    private Integer deliveryStatus;

    /** 数量 */
    @NotNull(message="数量不能为空")
    private Double qty;

    /** 单位 */
    @NotBlank(message="单位不能为空")
    @Length(min=1, max=4, message="单位长度必须介于 1 和 4 之间")
    private String unit;

    /** 订单单位 */
    @NotBlank(message="订单单位不能为空")
    @Length(min=1, max=4, message="订单单位长度必须介于 1 和 4 之间")
    private String orderUnit;

    /** 订单单位数量 */
    @NotNull(message="订单单位数量不能为空")
    private Double orderQty;

    /** 备注 */
    @Length(max=100, message="备注长度必须介于 0 和 100 之间")
    private String remark;

}
    