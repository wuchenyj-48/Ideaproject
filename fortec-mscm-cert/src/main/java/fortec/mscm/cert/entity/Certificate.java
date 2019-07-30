

package fortec.mscm.cert.entity;

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
* 预定义资质实体对象
* @author chenchen
*/
@TableName("certificate")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Certificate extends DataEntity  implements Serializable {

    /** 资质编码 */
    @NotBlank(message="资质编码不能为空")
    @Length(min=1, max=20, message="资质编码长度必须介于 1 和 20 之间")
    private String code;

    /** 资质名称 */
    @NotBlank(message="资质名称不能为空")
    @Length(min=1, max=30, message="资质名称长度必须介于 1 和 30 之间")
    private String name;

    /** 需要有效期 */
    @NotNull(message="需要有效期不能为空")
    private Integer needExpiryDate;

    /** 最大文件长度 */
    @NotNull(message="最大文件长度不能为空")
    private Integer maxiumFileSize;

    /** 最大文件数量 */
    @NotNull(message="最大文件数量不能为空")
    private Integer maxiumFileCount;

    /** 限制文件类型 */
    @NotBlank(message="限制文件类型不能为空")
    @Length(min=1, max=30, message="限制文件类型长度必须介于 1 和 30 之间")
    private String limitFileExtension;

}
    