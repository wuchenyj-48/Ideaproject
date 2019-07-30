

package fortec.mscm.cert.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
* 院方业务资质定义实体对象
* @author chenchen
*/
@TableName("certificate_hospital_business")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class CertificateHospitalBusiness extends DataEntity  implements Serializable {

    /** 院方ID */
    /**@NotBlank(message="院方ID不能为空")*/
    /**@Length(min=1, max=20, message="院方ID长度必须介于 1 和 20 之间")*/
    private String hospitalId;

    /** 业务编码 */
    @NotBlank(message="业务编码不能为空")
    @Length(min=1, max=10, message="业务编码长度必须介于 1 和 10 之间")
    private String businessTypeCode;

    /** 资质ID */
    @NotBlank(message="资质ID不能为空")
    @Length(min=1, max=20, message="资质ID长度必须介于 1 和 20 之间")
    private String certificateId;

    @TableField(exist = false)
    private String hospitalName;

    @TableField(exist = false)
    private String certificateName;

    @TableField(exist = false)
    private String code;

    @TableField(exist = false)
    private String limitFileExtension;

    @TableField(exist = false)
    private Integer needExpiryDate;

    @TableField(exist = false)
    private Integer maxiumFileSize;

    @TableField(exist = false)
    private Integer maxiumFileCount;

}
    