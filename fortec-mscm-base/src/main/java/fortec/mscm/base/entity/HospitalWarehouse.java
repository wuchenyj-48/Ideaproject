

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
* 医院收货地点实体对象
* @author yuntao.zhou
*/
@TableName("hospital_warehouse")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class HospitalWarehouse extends DataEntity  implements Serializable {

    /** 医院标识 */
    @NotNull(message="医院标识不能为空")
    private Long hospitalId;

    /** 仓库代码 */
    @NotBlank(message="仓库代码不能为空")
    @Length(min=1, max=200, message="收货地点长度必须介于 1 和 200 之间")
    private String code;

    /** 仓库名称 */
    @NotBlank(message="仓库名称不能为空")
    @Length(min=1, max=200, message="收货地点长度必须介于 1 和 200 之间")
    private String name;

}
    