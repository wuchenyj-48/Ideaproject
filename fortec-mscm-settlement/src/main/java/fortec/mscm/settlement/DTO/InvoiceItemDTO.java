
package fortec.mscm.settlement.DTO;

import fortec.common.core.model.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InvoiceItemDTO extends PageRequest {

    /** 开票单行ID */
    private String invoiceLineId;

    /** 供应商id */
    private String supplierId;

    /** 品名 */
    private String materialName;

    /** 规格 */
    private String materialSpec;

    /** 记账单号 */
    private String code;

    /** 医院id */
    private String hospitalId;
}
    