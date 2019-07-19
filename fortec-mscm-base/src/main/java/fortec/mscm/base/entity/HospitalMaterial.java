

package fortec.mscm.base.entity;

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
* HospitalMaterial实体对象
* @author chenchen
*/
@TableName("hospital_material")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class HospitalMaterial extends DataEntity  implements Serializable {

    /** 医院标识 */
    @NotNull(message="医院标识不能为空")
    private Long hospitalId;

    /** 商品ID */
    @NotNull(message="商品ID不能为空")
    private Long materialId;

    /** 商品规格ID */
    @NotNull(message="商品规格ID不能为空")
    private Long materialSpecId;

    /** 商品编码 */
    @Length(max=20, message="商品编码长度必须介于 0 和 20 之间")
    private String materialCode;

    /** 商品名称 */
    @Length(max=50, message="商品名称长度必须介于 0 和 50 之间")
    private String materialName;

    /** 商品通用名 */
    @Length(max=50, message="商品通用名长度必须介于 0 和 50 之间")
    private String materialTradeName;

    /** 价格 */
    private Long price;

    /** 最小请领单位 */
    @Length(max=20, message="最小请领单位长度必须介于 0 和 20 之间")
    private String miniumRequestUnit;

    /** 最小请领单位数量 */
    private Double miniumRequestQty;

    /** 最小订单单位 */
    @Length(max=20, message="最小订单单位长度必须介于 0 和 20 之间")
    private String miniumOrderUnit;

    /** 最小订单单位数量 */
    private Double miniumOrderQty;

    /** 停用标志 */
    @NotNull(message="停用标志不能为空")
    private Integer inactive;

}
    