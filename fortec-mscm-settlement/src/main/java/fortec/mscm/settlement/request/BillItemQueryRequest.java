
package fortec.mscm.settlement.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BillItemQueryRequest extends PageRequest {

    /** 记账单ID */
    private String billId;

}
    