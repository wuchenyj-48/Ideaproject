
package fortec.mscm.order.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PurchaseOrderQueryRequest extends PageRequest {

    /** 订单编号 */
    private String code;

    /** 供应商名称 */
    private String supplierName;

    /** 供应商确认状态  */
    private Integer supplierConfirmStatus;

    /** 发货状态 */
    private Integer deliveryStatus;

    /** 订单状态  */
    private Integer status;

    /** 创建时间 */
    private java.util.Date beginGmtCreate;
    private java.util.Date endGmtCreate;


}
    