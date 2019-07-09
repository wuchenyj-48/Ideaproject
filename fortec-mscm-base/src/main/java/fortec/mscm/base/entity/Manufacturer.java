

package fortec.mscm.base.entity;

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
* 厂商实体对象
* @author chenchen
*/
@TableName("manufacturer")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Manufacturer extends DataEntity  implements Serializable {

    /** 供应商ID */
    @NotNull(message="供应商ID不能为空")
    private Long supplierId;

    /** 厂商名称 */
    @NotBlank(message="厂商名称不能为空")
    @Length(min=1, max=100, message="厂商名称长度必须介于 1 和 100 之间")
    private String name;

    /** 社会信用代码 */
    @NotBlank(message="社会信用代码不能为空")
    @Length(min=1, max=30, message="社会信用代码长度必须介于 1 和 30 之间")
    private String companyCode;

    /** 生产许可证 */
    @Length(max=50, message="生产许可证长度必须介于 0 和 50 之间")
    private String productionLicence;

    /** 助记码 */
    @Length(max=20, message="助记码长度必须介于 0 和 20 之间")
    private String pinyin;

}
    