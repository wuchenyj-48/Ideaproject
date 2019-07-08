

package fortec.mscm.base.entity;

import java.io.Serializable;

import fortec.common.core.model.DataEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
/**
* 医院收货地点实体对象
* @author yuntao.zhou
*/
@TableName("hospital_location")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class HospitalLocation extends DataEntity  implements Serializable {

    /** 医院标识 */
    @NotNull(message="医院标识不能为空")
    private Long hospitalId;

    /** 收货地点 */
    @NotBlank(message="收货地点不能为空")
    @Length(min=1, max=200, message="收货地点长度必须介于 1 和 200 之间")
    private String locationName;

}
    