
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MaterialSpecQueryRequest extends PageRequest {

    /** 规格 */
    private String materialSpec;

    /** 价格 */
    private Double beginPrice;
    private Double endPrice;


}
    