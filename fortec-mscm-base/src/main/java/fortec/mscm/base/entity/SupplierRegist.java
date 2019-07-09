

package fortec.mscm.base.entity;

import java.io.Serializable;

import fortec.common.core.model.DataEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;


import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
/**
* 实体对象
* @author chenchen
*/
@TableName("supplier_regist")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class SupplierRegist extends DataEntity  implements Serializable {

    /** 统一社会信用代码 */
    @NotBlank(message="统一社会信用代码不能为空")
    @Length(min=1, max=30, message="统一社会信用代码长度必须介于 1 和 30 之间")
    private String companyCode;

    /** 供应商名称 */
    @NotBlank(message="供应商名称不能为空")
    @Length(min=1, max=50, message="供应商名称长度必须介于 1 和 50 之间")
    private String name;

    /** 申请人 */
    @NotBlank(message="申请人不能为空")
    @Length(min=1, max=50, message="申请人长度必须介于 1 和 50 之间")
    private String applicant;

    /** 申请人手机 */
    @NotBlank(message="申请人手机不能为空")
    @Length(min=1, max=20, message="申请人手机长度必须介于 1 和 20 之间")
    private String applicantMobile;

    /** 申请人邮箱 */
    @NotBlank(message="申请人邮箱不能为空")
    @Length(min=1, max=50, message="申请人邮箱长度必须介于 1 和 50 之间")
    private String applicantEmail;

    /** 区域id */
    private Long regionId;

    /** 是否药品供应商 */
    @NotNull(message="是否药品供应商不能为空")
    private Integer isDrug;

    /** 是否耗材供应商 */
    @NotNull(message="是否耗材供应商不能为空")
    private Integer isConsumable;

    /** 是否试剂供应商 */
    @NotNull(message="是否试剂供应商不能为空")
    private Integer isReagent;

    /** 审核状态 : 0：未提交，1：待审核，2：审核通过。字典类型：base_supplier_regist_astatus */
    @NotNull(message="审核状态 : 0：未提交，1：待审核，2：审核通过。字典类型：base_supplier_regist_astatus不能为空")
    private Integer astatus;

}
    