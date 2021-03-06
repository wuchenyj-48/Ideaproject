

package fortec.mscm.base.entity;

import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
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
* 商品规格实体对象
* @author chenchen
*/
@TableName("material_spec")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
@ExcelTarget("materialSpec")
public class MaterialSpec extends DataEntity  implements Serializable {

    /** 商品ID */
    @NotNull(message="商品ID不能为空")
    private String materialId;

    /** 规格 */
    @NotBlank(message="规格不能为空")
    @Length(min=1, max=100, message="规格长度必须介于 1 和 100 之间")
    private String materialSpec;

    /** 编码格式 */
    @NotNull(message="编码格式不能为空")
    private Integer inputCodingType;

    /** 输入编码 */
    @Length(max=30, message="输入编码长度必须介于 0 和 30 之间")
    private String inputCode;

    /** 剂型 */
    @Length(max=30, message="剂型长度必须介于 0 和 30 之间")
    private String form;

    /** 单位 */
    private String unit;

    /** 价格 */
    private Double price;

   /** 是否寄售 */
    @NotNull(message="是否寄售不能为空")
    private Integer isConsignment;

    /** 是否一物一码*/
    @NotNull(message="是否一物一码不能为空")
    private Integer isOneThingOneYard;

    /** 小包装单位 */
    private String smallPackageUnit;

    /** 小包装单位数量 */
    private Integer smallPackageQty;

    /** 中包装单位 */
    private String mediumPackageUnit;

    /** 中包装单位数量 */
    private Integer mediumPackageQty;

    /** 大包装单位 */
    private String largePackageUnit;

    /** 大包装单位数量 */
    private Integer largePackageQty;

    /**供应商标识*/
    @TableField(exist = false)
    private String supplierId;

    /**品名*/
    @TableField(exist = false)
    private String materialName;

   /**商品名*/
    @TableField(exist = false)
    private String materialTradeName;

}
    