

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
* 库存管理实体对象
* @author chenchen
*/
@TableName("stock")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Stock extends DataEntity  implements Serializable {

    /** 医院ID */
    private String hospitalId;

    /** 医院名称 */
    private String hospitalName;

    /** 供应商ID */
    private String supplierId;

    /** 供应商名称 */
    private String supplierName;

    /** 仓库ID */
    @NotNull(message="仓库ID不能为空")
    private String warehouseId;

    /** 仓库名称 */
    @NotBlank(message="仓库名称不能为空")
    @Length(min=1, max=100, message="仓库名称长度必须介于 1 和 100 之间")
    private String warehouseName;

    /** 商品ID */
    @NotNull(message="商品ID不能为空")
    private Long materialId;

    /** 品规ID */
    @NotNull(message="品规ID不能为空")
    private Long materialSpecId;

    /** 品名 */
    @NotBlank(message="品名不能为空")
    @Length(min=1, max=100, message="品名长度必须介于 1 和 100 之间")
    private String materialName;

    /** 品规 */
    @NotBlank(message="品规不能为空")
    @Length(min=1, max=100, message="品规长度必须介于 1 和 100 之间")
    private String materialSpec;

    /** 批次 */
    @NotBlank(message="批次不能为空")
    @Length(min=1, max=50, message="批次长度必须介于 1 和 50 之间")
    private String lot;

    /** 数量 */
    @NotNull(message="数量不能为空")
    private Double qty;

    /** 单位 */
    @NotBlank(message="单位不能为空")
    @Length(min=1, max=4, message="单位长度必须介于 1 和 4 之间")
    private String unit;

    /** 单价(元) */
    @NotNull(message="单价(元)不能为空")
    private Double price;

    /** 序列号 */
    @Length(max=50, message="序列号长度必须介于 0 和 50 之间")
    private String sn;

    /** 效期 */
    private java.util.Date expiredDate;

}
    