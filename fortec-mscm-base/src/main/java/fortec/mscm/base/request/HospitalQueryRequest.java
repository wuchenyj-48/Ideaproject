
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HospitalQueryRequest extends PageRequest {

    /** 医院代码 */
    private String code;

    /** 医院名称 */
    private String name;


}
    