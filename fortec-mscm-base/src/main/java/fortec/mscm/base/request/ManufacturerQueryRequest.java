
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManufacturerQueryRequest extends PageRequest {

    /** 供应商ID */
    private Long supplierId;


}
    