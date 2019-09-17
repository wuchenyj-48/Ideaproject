

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
public class SupplierVO extends DataEntity  implements Serializable {

    /** 机构ID */
    //@NotBlank(message="组织机构不能为空")
    private String officeId;

    /** 识别码 */
    @ExcelField(title = "识别码",align = FieldAlign.CENTER, sort = 10)
    @NotBlank(message="识别码不能为空")
    @Length(min=1, max=7, message="识别码长度必须介于 1 和 7 之间")
    private String code;

    /** 统一社会信用代码 */
    @ExcelField(title = "统一社会信用代码",align = FieldAlign.CENTER, sort = 20)
    @NotBlank(message="统一社会信用代码不能为空")
    @Length(min=1, max=30, message="统一社会信用代码长度必须介于 1 和 30 之间")
    private String companyCode;

    /** 供应商名称 */
    @ExcelField(title = "供应商名称",align = FieldAlign.CENTER, sort = 30)
    @NotBlank(message="供应商名称不能为空")
    @Length(min=1, max=50, message="供应商名称长度必须介于 1 和 50 之间")
    private String name;

    /** 助记码 */
    @ExcelField(title = "助记码",align = FieldAlign.CENTER, sort = 40)
    @Length(max=20, message="助记码长度必须介于 0 和 20 之间")
    private String pinyin;

    /** 是否药品供应商 */
    @ExcelField(title = "是否药品供应商",align = FieldAlign.CENTER, sort = 50,dictType = "common_yes_no")
    @NotNull(message="是否药品供应商不能为空")
    private Integer isDrug;

    /** 是否耗材供应商 */
    @ExcelField(title = "是否耗材供应商",align = FieldAlign.CENTER, sort = 60,dictType = "common_yes_no")
    @NotNull(message="是否耗材供应商不能为空")
    private Integer isConsumable;

    /** 是否试剂供应商 */
    @ExcelField(title = "是否试剂供应商",align = FieldAlign.CENTER, sort = 70,dictType = "common_yes_no")
    @NotNull(message="是否试剂供应商不能为空")
    private Integer isReagent;

    /** 区域id */
    @ExcelField(title = "区域id",align = FieldAlign.CENTER, sort = 80)
    private Long regionId;

    /** 地址 */
    @ExcelField(title = "地址",align = FieldAlign.CENTER, sort = 90)
    @NotBlank(message="地址不能为空")
    @Length(min=1, max=200, message="地址长度必须介于 1 和 200 之间")
    private String address;

    /** 联系人 */
    @ExcelField(title = "联系人",align = FieldAlign.CENTER, sort = 100)
    @NotBlank(message="联系人不能为空")
    @Length(min=1, max=50, message="联系人长度必须介于 1 和 50 之间")
    private String contactor;

    /** 邮箱 */
    @ExcelField(title = "邮箱",align = FieldAlign.CENTER, sort = 110)
    @NotBlank(message="邮箱不能为空")
    @Length(min=1, max=50, message="邮箱长度必须介于 1 和 50 之间")
    private String email;

    /** 电话 */
    @ExcelField(title = "电话",align = FieldAlign.CENTER, sort = 120)
    @Length(max=20, message="电话长度必须介于 0 和 20 之间")
    private String phone;

    /** 移动电话 */
    @ExcelField(title = "移动电话",align = FieldAlign.CENTER, sort = 130)
    @Length(max=20, message="移动电话长度必须介于 0 和 20 之间")
    private String mobile;

}
    