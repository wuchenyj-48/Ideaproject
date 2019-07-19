
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierApplicantQueryRequest extends PageRequest {

    /** 单据号 */
    private String code;

    /** 单据状态 */
    private Integer status;


}
    