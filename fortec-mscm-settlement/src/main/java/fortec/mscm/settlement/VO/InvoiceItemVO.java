

package fortec.mscm.settlement.VO;

import com.baomidou.mybatisplus.annotation.TableName;
import fortec.common.core.model.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
* 发票单明细实体对象
* @author chenchen
*/
@TableName("invoice_item")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class InvoiceItemVO extends DataEntity  implements Serializable {

    /** 开票单ID */
    private Long invoiceId;

    /** 开票单行ID */
    private Long invoiceLineId;

    /** 记账单ID */
    private Long billId;

    /** 记账单明细ID */
    private Long billItemId;

    /** 备注 */
    private String remark;

    /** 品名 */
    private String materialName;

    /** 商品通用名 */
    private String materialTradeName;

    /** 规格 */
    private String materialSpec;

    /** 厂商名称 */
    private String manufacturerName;

    /** 剂型 */
    private String form;

    /** 价格 */
    private Double price;

    /** 数量 */
    private Double qty;

    /** 批次 */
    private String lot;

    /** 单位 */
    private String unit;

    /** 小计(元) */
    private Double subtotalAmount;

    /** 记账单号 */
    private String code;

    /** 记账单时间 */
    private Date billGmtCreate;


}
    