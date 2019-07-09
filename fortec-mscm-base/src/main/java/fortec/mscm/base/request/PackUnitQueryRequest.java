
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PackUnitQueryRequest extends PageRequest {

    /** 单位编码 */
    private String id;

    /** 单位名称 */
    private String name;


}
    