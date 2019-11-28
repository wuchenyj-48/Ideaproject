package fortec.mscm.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
import java.util.Date;

/**
 * @Description:
 * @Author: chen.wu
 * @CreateDate: 2019-11-12 10:55
 * Version:      2.4
 */
@TableName("supplier")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class SupplierStatistics extends DataEntity implements Serializable {
    /** 医院编号 */
    @TableField(exist = false)
    private String hospitalName;

    /** 医院ID */
    @TableField(exist = false)
    private String hospitalId;

    /** 供应商ID */
    @TableField(exist = false)
    private  String supplierId;

    /** 供应商ID */
    private String id;

    /** 识别码 */
    private String code;

    /** 供应商名称 */
    @Length(min=1, max=100, message="单位名称长度必须介于 1 和 50 之间")
    private String name;

    /** 联系人 */
    private String contactor;

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;

    /** 移动电话 */
    private String mobile;

    /** 状态 */
    @TableField(exist = false)
    private String inactive;

    /** 加入时间 */
    private Date gmtModified;




}
