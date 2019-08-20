

package fortec.mscm.cert.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
* 供应商资质仓库实体对象
* @author chenchen
*/
@TableName("certificate_repository")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class CertificateRepository extends DataEntity  implements Serializable {

    /** 供应商ID */
    /** @NotBlank(message="供应商ID不能为空")*/
    @Length(min=1, max=20, message="供应商ID长度必须介于 1 和 20 之间")
    private String supplierId;

    /** 资质类型 */
    /*@NotBlank(message="资质类型不能为空")*/
    @Length(min=1, max=10, message="资质类型长度必须介于 1 和 10 之间")
    private String businessTypeCode;

    /** 目标ID */
    /** @NotBlank(message="目标ID不能为空")*/
    @Length(min=1, max=20, message="目标ID长度必须介于 1 和 20 之间")
    private String targetDescribeId;

    /** 资质ID */
    @Length(max=20, message="资质ID长度必须介于 0 和 20 之间")
    @NotBlank(message = "资质名称不能为空")
    private String certificateId;

    /** 资质编号 */
    @Length(max=30, message="资质编号长度必须介于 0 和 30 之间")
    private String certificateNo;

    /** 有效期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private java.util.Date expiryDate;

    /** 签发方 */
    @Length(max=50, message="签发方长度必须介于 0 和 50 之间")
    private String certificateSign;

    /** 签发给 */
    @Length(max=50, message="签发给长度必须介于 0 和 50 之间")
    private String certificateSignTo;

    /** 文档IDs */
    @NotNull(message = "资质文件不能为空")
    private JSONArray docIds;

    /** 当前版本号 */
    private Integer version;

    /** 资质名称 */
    @TableField(exist = false)
    private String certificateName;

    /** 限制文件类型 */
    @TableField(exist = false)
    private String limitFileExtension;

    /** 是否需要有效期 */
    @TableField(exist = false)
    private String needExpiryDate;

    /** 文件大小上限 */
    @TableField(exist = false)
    private String maxiumFileSize;

    /** 文件数上限 */
    @TableField(exist = false)
    private String maxiumFileCount;

    /** 关闭状态 */
    private Integer closeFlag;

    /** 厂商id */
    private String manufacturerId;

    /** 厂商名称 */
    @TableField(exist = false)
    private String manufacturerName;

    public static final int CLOSE_FLAG_NORMAL = 0;
    public static final int CLOSE_FLAG_CLOSED = 1;

}
    