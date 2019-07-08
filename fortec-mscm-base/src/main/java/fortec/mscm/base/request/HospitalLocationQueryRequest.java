
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HospitalLocationQueryRequest extends PageRequest {

    /** 医院标识 */
    private Long hospitalId;


}
    