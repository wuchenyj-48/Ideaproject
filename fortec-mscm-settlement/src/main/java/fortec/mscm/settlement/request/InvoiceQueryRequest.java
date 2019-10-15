
package fortec.mscm.settlement.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InvoiceQueryRequest extends PageRequest {

    /** 单号 */
    private String code;

    /** 院方名称 */
    private String hospitalName;

    /** 供应商id */
    private String supplierId;

    /** 开票时间 */
    private java.util.Date beginGmtMakeOut;
    private java.util.Date endGmtMakeOut;

    /** 状态 */
    private Integer status;

}
    