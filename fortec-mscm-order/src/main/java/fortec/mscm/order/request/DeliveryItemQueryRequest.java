
package fortec.mscm.order.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeliveryItemQueryRequest extends PageRequest {

    /** 发货单ID */
    private Long deliveryId;


}
    