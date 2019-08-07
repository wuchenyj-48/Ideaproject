

package fortec.mscm.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
* 医院实体对象
* @author yuntao.zhou
*/
@TableName("hospital")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Hospital extends DataEntity  implements Serializable {

    /** 机构ID */
    @JSONField(serialize = false,deserialize = false)
    private String officeId;

    /** 医院代码 */
//    @NotBlank(message="医院代码不能为空")
//    @Length(min=1, max=7, message="医院代码长度必须介于 1 和 7 之间")
    private String code;

    /** 医院名称 */
    @NotBlank(message="医院名称不能为空")
    @Length(min=1, max=50, message="医院名称长度必须介于 1 和 50 之间")
    private String name;

    /** 简称 */
    @Length(max=50, message="简称长度必须介于 0 和 50 之间")
    private String shortName;

    /** 拼音 */
    @NotBlank(message="拼音不能为空")
    @Length(min=1, max=20, message="拼音长度必须介于 1 和 20 之间")
    private String pinyin;

    /** 区域id */
    private Long regionId;

    /** 地址 */
    @Length(max=200, message="地址长度必须介于 0 和 200 之间")
    private String address;

    /** 联系人 */
    @Length(max=50, message="联系人长度必须介于 0 和 50 之间")
    private String contactor;

    /** 邮箱 */
    @Length(max=50, message="邮箱长度必须介于 0 和 50 之间")
    private String email;

    /** 电话 */
    @Length(max=20, message="电话长度必须介于 0 和 20 之间")
    private String phone;

}
    