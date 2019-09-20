
package fortec.mscm.order.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeliveryQueryRequest extends PageRequest {

    /** 发货单号 */
    private String code;

    /** 采购单号 */
    private String poCode;


}
    