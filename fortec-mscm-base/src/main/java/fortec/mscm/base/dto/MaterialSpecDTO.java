

package fortec.mscm.base.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
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
 * 商品规格实体对象
 *
 * @author chenchen
 */
@TableName("material_spec")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
@ExcelTarget("materialSpec")
public class MaterialSpecDTO extends DataEntity implements Serializable {

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private String materialId;


    @Excel(name = "规格", orderNum = "10")
    @NotBlank(message = "规格不能为空")
    @Length(min = 1, max = 100, message = "规格长度必须介于 1 和 100 之间")
    private String materialSpec;


    @Excel(name = "编码格式", orderNum = "20",dict = "base_material_coding_type")
    @NotNull(message = "编码格式不能为空")
    private Integer inputCodingType;


    @Excel(name = "输入编码", orderNum = "30")
    @Length(max = 30, message = "输入编码长度必须介于 0 和 30 之间")
    private String inputCode;


    @Excel(name = "剂型", orderNum = "40")
    @Length(max = 30, message = "剂型长度必须介于 0 和 30 之间")
    private String form;

    @Excel(name = "单位", orderNum = "50")
    private String unit;


    @Excel(name = "价格(元)", orderNum = "60")
    private Double price;


    @Excel(name = "寄售", orderNum = "70", dict = "common_yes_no")
    @NotNull(message = "是否寄售不能为空")
    private Integer isConsignment;


    @Excel(name = "一物一码", orderNum = "80", dict = "common_yes_no")
    @NotNull(message = "是否一物一码不能为空")
    private Integer isOneThingOneYard;

    private String supplierId;


    private String materialName;


    private String materialTradeName;

}
    