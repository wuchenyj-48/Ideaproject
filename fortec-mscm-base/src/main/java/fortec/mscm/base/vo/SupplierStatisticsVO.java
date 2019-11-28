package fortec.mscm.base.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.TreeEntity;
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
import java.util.Date;

/**
 * @Description:
 * @Author: chen.wu
 * @CreateDate: 2019-11-18 11:19
 * Version:      2.4
 */
@TableName("Supplier")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class SupplierStatisticsVO extends TreeEntity implements Serializable {


    /** 识别码 */
    @ExcelField(title = "识别码",align = FieldAlign.CENTER, sort = 10)
    @NotBlank(message="识别码不能为空")
    @Length(min=1, max=7, message="识别码长度必须介于 1 和 7 之间")
    private String code;


    /** 供应商名称 */
    @ExcelField(title = "供应商名称",align = FieldAlign.CENTER, sort = 100)
    @NotBlank(message="供应商名称不能为空")
    @Length(min=1, max=50, message="供应商名称长度必须介于 1 和 50 之间")
    private String name;

    /** 联系人 */
    @ExcelField(title = "联系人",align = FieldAlign.CENTER, sort = 100)
    @NotBlank(message="联系人不能为空")
    @Length(min=1, max=50, message="联系人长度必须介于 1 和 50 之间")
    private String contactor;

    /** 移动电话 */
    @ExcelField(title = "手机号",align = FieldAlign.CENTER, sort = 130)
    @Length(max=20, message="移动电话长度必须介于 0 和 20 之间")
    private String mobile;

    /** 邮箱 */
    @ExcelField(title = "邮箱",align = FieldAlign.CENTER, sort = 110)
    @NotBlank(message="邮箱不能为空")
    @Length(min=1, max=50, message="邮箱长度必须介于 1 和 50 之间")
    private String email;

    /** 状态 */
    @ExcelField(title = "状态" ,align=FieldAlign.CENTER,sort=110)
    @NotBlank(message="状态不能为空")
    @TableField(exist = false)
    @Length(max=20, message="移动电话长度必须介于 0 和 20 之间")
    private String inactive;

    /** 加入时间 */
    @ExcelField(title = "加入时间",align = FieldAlign.CENTER, sort=130)
    @Length(max = 20)
    private Date gmtModified;
}
