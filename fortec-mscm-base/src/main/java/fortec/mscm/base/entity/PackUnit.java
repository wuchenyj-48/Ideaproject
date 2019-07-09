

package fortec.mscm.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.DataEntity;
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
public class PackUnit extends DataEntity  implements Serializable {

    /** 单位编码*/
    @NotBlank(message="单位编码不能为空")
    @Length(min=1, max=20, message="单位编码长度必须介于 1 和 50 之间")
    @TableId(type= IdType.INPUT)
    private String id;


    /** 单位名称 */
    @NotBlank(message="单位名称不能为空")
    @Length(min=1, max=50, message="单位名称长度必须介于 1 和 50 之间")
    private String name;

}
    