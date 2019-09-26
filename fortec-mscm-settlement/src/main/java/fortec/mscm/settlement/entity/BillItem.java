

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
* 记账单明细实体对象
* @author hl
*/
@TableName("bill_item")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class BillItem extends DataEntity  implements Serializable {

    /** 记账单ID */
    @NotBlank(message="记账单ID不能为空")
    @Length(min=1, max=20, message="记账单ID长度必须介于 1 和 20 之间")
    private String billId;

    /** 商品规格ID */
    @NotBlank(message="商品规格ID不能为空")
    @Length(min=1, max=20, message="商品规格ID长度必须介于 1 和 20 之间")
    private String materialSpecId;

    /** 商品编码 */
    @Length(max=20, message="商品编码长度必须介于 0 和 20 之间")
    private String materialCode;

    /** 商品代码 */
    @Length(max=20, message="商品代码长度必须介于 0 和 20 之间")
    private String materialErpCode;

    /** 品名 */
    @Length(max=100, message="品名长度必须介于 0 和 100 之间")
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
    private Double price;

    /** 数量 */
    @NotNull(message="数量不能为空")
    private Double qty;

    /** 批次 */
    @Length(max=50, message="批次长度必须介于 0 和 50 之间")
    private String lot;

    /** 单位 */
    @NotBlank(message="单位不能为空")
    @Length(min=1, max=4, message="单位长度必须介于 1 和 4 之间")
    private String unit;

    /** 小计(元) */
    @NotNull(message="小计(元)不能为空")
    private Double subtotalAmount;

    /** 备注 */
    @Length(max=200, message="备注长度必须介于 0 和 200 之间")
    private String remark;

}
    