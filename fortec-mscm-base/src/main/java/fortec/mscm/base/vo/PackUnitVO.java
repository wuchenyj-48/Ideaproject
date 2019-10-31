

package fortec.mscm.base.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.DataEntity;
import fortec.common.core.utils.excel.annotation.ExcelField;
import fortec.common.core.utils.excel.consts.FieldAlign;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
* 包装单位实体对象
* @author yuntao.zhou
*/
@TableName("pack_unit")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PackUnitVO extends DataEntity  implements Serializable {

    /** 单位编码*/
    @ExcelField(title = "单位编码", align = FieldAlign.CENTER, sort = 10)
    @NotBlank(message="单位编码不能为空")
    @Length(min=1, max=20, message="单位编码长度必须介于 1 和 50 之间")
    private String id;


    /** 单位名称 */
    @ExcelField(title = "单位编码", align = FieldAlign.CENTER, sort = 20)
    @NotBlank(message="单位名称不能为空")
    @Length(min=1, max=50, message="单位名称长度必须介于 1 和 50 之间")
    private String name;

}
    