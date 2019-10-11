

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
* 供应商实体对象
* @author chenchen
*/
@TableName("supplier")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Supplier extends DataEntity  implements Serializable {

    /** 机构ID */
    //@NotBlank(message="组织机构不能为空")
    private String officeId;

    /** 识别码 */
    @NotBlank(message="识别码不能为空")
    @Length(min=1, max=7, message="识别码长度必须介于 1 和 7 之间")
    private String code;

    /** 统一社会信用代码 */
    @NotBlank(message="统一社会信用代码不能为空")
    @Length(min=1, max=30, message="统一社会信用代码长度必须介于 1 和 30 之间")
    private String companyCode;

    /** 供应商名称 */
    @NotBlank(message="供应商名称不能为空")
    @Length(min=1, max=50, message="供应商名称长度必须介于 1 和 50 之间")
    private String name;

    /** 助记码 */
    @Length(max=20, message="助记码长度必须介于 0 和 20 之间")
    private String pinyin;

    /** 是否药品供应商 */
    @NotNull(message="是否药品供应商不能为空")
    private Integer isDrug;

    /** 是否耗材供应商 */
    @NotNull(message="是否耗材供应商不能为空")
    private Integer isConsumable;

    /** 是否试剂供应商 */
    @NotNull(message="是否试剂供应商不能为空")
    private Integer isReagent;

    /** 区域id */
    private Long regionId;

    /** 区域名称 */
    private String regionName;

    /** 地址 */
    @NotBlank(message="地址不能为空")
    @Length(min=1, max=200, message="地址长度必须介于 1 和 200 之间")
    private String address;

    /** 联系人 */
    @NotBlank(message="联系人不能为空")
    @Length(min=1, max=50, message="联系人长度必须介于 1 和 50 之间")
    private String contactor;

    /** 邮箱 */
    @NotBlank(message="邮箱不能为空")
    @Length(min=1, max=50, message="邮箱长度必须介于 1 和 50 之间")
    private String email;

    /** 电话 */
    @Length(max=20, message="电话长度必须介于 0 和 20 之间")
    private String phone;

    /** 移动电话 */
    @Length(max=20, message="移动电话长度必须介于 0 和 20 之间")
    private String mobile;

}
    