
package fortec.mscm.settlement.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BillQueryRequest extends PageRequest {

    /** 账单状态 */
    private Integer status;

    /** 记账日期 */
    private java.util.Date beginGmtCreate;
    private java.util.Date endGmtCreate;

    /** 院方id */
    private String hospitalId;

    /** 供方id */
    private String supplierId;


}
    