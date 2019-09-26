

package fortec.mscm.settlement.entity;

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
* 记账单实体对象
* @author HL
*/
@TableName("bill")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Bill extends DataEntity  implements Serializable {

    /** 院方ID */
    private String hospitalId;

    /** 院方名称 */
    private String hospitalName;

    /** 记帐单号 */
    private String code;

    /** 供应商ID */
    @NotNull(message="供应商ID不能为空")
    private String supplierId;

    /** 供应商名称 */
    @NotBlank(message="供应商名称不能为空")
    @Length(min=1, max=50, message="供应商名称长度必须介于 1 和 50 之间")
    private String supplierName;

    /** 院方科室代码 */
    @Length(max=20, message="院方科室代码长度必须介于 0 和 20 之间")
    private String deptCode;

    /** 科室名称 */
    @NotBlank(message="科室名称不能为空")
    @Length(min=1, max=50, message="科室名称长度必须介于 1 和 50 之间")
    private String deptName;

    /** 寄售 */
    @NotNull(message="寄售不能为空")
    private Integer isConsignment;

    /** 一物一码  */
    @NotNull(message="一物一码 不能为空")
    private Integer isOneThingOneYard;

    /** 记账金额(元) */
    private Double totalAmount;

    /** 账单状态 */
    private Integer status;

    /** 备注 */
    private String remark;

}
    