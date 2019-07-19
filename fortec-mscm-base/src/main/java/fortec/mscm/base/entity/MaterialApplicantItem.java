

package fortec.mscm.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
* MaterialApplicantItem实体对象
* @author chenchen
*/
@TableName("material_applicant_item")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class MaterialApplicantItem extends DataEntity  implements Serializable {

    /** 主表ID */
    @NotNull(message="主表ID不能为空")
    private Long applicantId;

    /** 商品规格ID */
    @NotNull(message="商品规格ID不能为空")
    private Long materialSpecId;

    /**商品规格*/
    @TableField(exist = false)
    private String materialSpec;

    /**价格*/
    @TableField(exist = false)
    private String price;

    /**单价*/
    @TableField(exist = false)
    private String unit;

    /**品名*/
    @TableField(exist = false)
    private String materialName;

    /**商品名*/
    @TableField(exist = false)
    private String materialTradeName;

}
    