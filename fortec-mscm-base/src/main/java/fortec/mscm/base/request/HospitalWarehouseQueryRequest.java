
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HospitalWarehouseQueryRequest extends PageRequest {

    /** 医院标识 */
    private Long hospitalId;

    /** 关键字搜索 */
    private String keywords;

    /*** 仓库名称 */
    private String name;

    /** 仓库代码 */
    private String code;

}
    