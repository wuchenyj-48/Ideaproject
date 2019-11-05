

package fortec.mscm.base.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
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
import java.util.List;

/**
* 商品实体对象
* @author chenchen
*/
@TableName("material")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class MaterialDTO extends DataEntity  implements Serializable {

    /** 供应商标识 */
    private String supplierId;

    /** 品类ID */
    @NotNull(message="品类ID不能为空")
    private String catalogId;

    /** 生产厂商 */
    private String manufacturerId;

    @Excel(name = "品类代码", needMerge = true)
    @NotNull(message="品类代码不能为空")
    private String catalogCode;

    @Excel(name = "品类名称", width = 25, needMerge = true)
    @NotNull(message="品类名称不能为空")
    private String catalogName;

    @Excel(name = "厂商代码", needMerge = true)
    @NotNull(message="厂商代码不能为空")
    private String manufacturerCompanyCode;

    @Excel(name = "厂商名称", width = 29, needMerge = true)
    @NotNull(message="厂商名称不能为空")
    private String manufacturerName;

    /** 商品类型 */
    @Excel(name = "商品类型", replace = { "药品_1", "耗材_2", "试剂_3" },needMerge = true)
    @NotNull(message="商品类型不能为空")
    private Integer materialTypeCode;

    /** 品名 */
    @Excel(name = "品名", width = 25, needMerge = true)
    @NotBlank(message="品名不能为空")
    @Length(min=1, max=50, message="品名长度必须介于 1 和 50 之间")
    private String materialName;

    /** 商品名 */
    @Excel(name = "商品名", width = 25, needMerge = true)
    @NotBlank(message="商品名不能为空")
    @Length(min=1, max=50, message="商品名长度必须介于 1 和 50 之间")
    private String materialTradeName;

    /** ERP代码 */
    @Excel(name = "ERP代码", needMerge = true)
    @Length(max=30, message="ERP代码长度必须介于 0 和 30 之间")
    private String materialErpCode;

    /** 助记码 */
    @Excel(name = "助记码", needMerge = true)
    @Length(max=20, message="助记码长度必须介于 0 和 20 之间")
    private String pinyin;

    /** 注册证号 */
    @Excel(name = "注册证号", width = 25, needMerge = true)
    @Length(max=50, message="注册证号长度必须介于 0 和 50 之间")
    private String certificateNo;

    /** 注册证效期 */
    @Excel(name = "注册证效期", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd",needMerge = true)
    private java.util.Date certificateExpiredDate;

    /** 批准文号 */
    @Excel(name = "批准文号", needMerge = true)
    @Length(max=50, message="批准文号长度必须介于 0 和 50 之间")
    private String approvalNo;

    @ExcelCollection(name = "规格")
    private List<MaterialSpecDTO> specs;

    private String id;

}
    