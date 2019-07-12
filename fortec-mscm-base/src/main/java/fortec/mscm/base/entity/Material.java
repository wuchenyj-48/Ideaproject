

package fortec.mscm.base.entity;

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
* 商品实体对象
* @author chenchen
*/
@TableName("material")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Material extends DataEntity  implements Serializable {

    /** 供应商标识 */
    @NotNull(message="供应商标识不能为空")
    private String supplierId;

    /** 品类ID */
    @NotNull(message="品类ID不能为空")
    private String catalogId;

    /** 商品类型 */
    @NotNull(message="商品类型不能为空")
    private Integer materialTypeCode;

    /** 品名 */
    @NotBlank(message="品名不能为空")
    @Length(min=1, max=50, message="品名长度必须介于 1 和 50 之间")
    private String materialName;

    /** 商品名 */
    @NotBlank(message="商品名不能为空")
    @Length(min=1, max=50, message="商品名长度必须介于 1 和 50 之间")
    private String materialTradeName;

    /** ERP代码 */
    @Length(max=30, message="ERP代码长度必须介于 0 和 30 之间")
    private String materialErpCode;

    /** 助记码 */
    @Length(max=20, message="助记码长度必须介于 0 和 20 之间")
    private String pinyin;

    /** 注册证号 */
    @Length(max=50, message="注册证号长度必须介于 0 和 50 之间")
    private String certificateNo;

    /** 注册证效期 */
    private java.util.Date certificateExpiredDate;

    /** 批准文号 */
    @Length(max=50, message="批准文号长度必须介于 0 和 50 之间")
    private String approvalNo;

    /** 生产厂商 */
    private String manufacturerId;


    @TableField(exist = false)
    private String supplierName;

    @TableField(exist = false)
    private String catalogName;

    @TableField(exist = false)
    private String manufacturerName;


}
    