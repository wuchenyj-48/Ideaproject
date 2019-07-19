
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HospitalMaterialQueryRequest extends PageRequest {

    /** 医院标识 */
    private Long hospitalId;

    /** 商品名称 */
    private String materialName;

    /** 价格 */
    private Long beginPrice;
    private Long endPrice;

    /** 停用标志 */
    private Integer inactive;


}
    