

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
* 发货单明细实体对象
* @author Yangjy
*/
@TableName("delivery_item")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeliveryItem extends DataEntity  implements Serializable {

    /** 发货单ID */
    @NotNull(message="发货单ID不能为空")
    private Long deliveryId;

    /** 订单明细ID */
    @NotNull(message="订单明细ID不能为空")
    private Long poItemId;

    /** 商品规格ID */
    @NotNull(message="商品规格ID不能为空")
    private Long materialSpecId;

    /** 商品编码 */
    @NotNull(message="商品编码不能为空")
    private Long materialCode;

    /** 商品代码 */
    @NotBlank(message="商品代码不能为空")
    @Length(min=1, max=20, message="商品代码长度必须介于 1 和 20 之间")
    private String materialErpCode;

    /** 商品名称 */
    @NotBlank(message="商品名称不能为空")
    @Length(min=1, max=100, message="商品名称长度必须介于 1 和 100 之间")
    private String materialName;

    /** 商品通用名 */
    @NotBlank(message="商品通用名不能为空")
    @Length(min=1, max=100, message="商品通用名长度必须介于 1 和 100 之间")
    private String materialTradeName;

    /** 规格 */
    @Length(min=1, max=100, message="规格长度必须介于 1 和 100 之间")
    private String materialSpec;

    /** 厂商名称 */
    @NotBlank(message="厂商名称不能为空")
    @Length(min=1, max=50, message="厂商名称长度必须介于 1 和 50 之间")
    private String manufacturerName;

    /** 剂型 */
    private String form;

    /** 价格 */
    @NotNull(message="价格不能为空")
    private Double price;

    /** 应发数量 */
    @NotNull(message="应发数量不能为空")
    private Double shouldSendQty;

    /** 已发数量 */
    @NotNull(message="已发数量不能为空")
    private Double sendedQty;

    /** 本次实发 */
    private Double qty;

    /** 小计(元) */
    private Double subtotalAmount;

    /** 单位 */
    @Length(min=1, max=4, message="单位长度必须介于 1 和 4 之间")
    private String unit;

    /** 备注 */
    @Length(max=100, message="备注长度必须介于 0 和 100 之间")
    private String remark;

}
    