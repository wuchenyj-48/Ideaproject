
package fortec.mscm.settlement.DTO;

import fortec.common.core.model.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BatchDeleteDTO extends PageRequest {

    /** 删除ids */
    private String[] ids;

    /** 开票单行id */
    private String invoiceLineId;

}
    