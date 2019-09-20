
package fortec.mscm.order.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PurchaseOrderItemQueryRequest extends PageRequest {

    //订单编号
    private String poId;


}
    