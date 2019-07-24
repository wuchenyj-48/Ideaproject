
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierQueryRequest extends PageRequest {

    /** 统一社会信用代码 */
    private String companyCode;

    /** 供应商名称 */
    private String name;

    /** 是否药品供应商 */
    private Integer isDrug;

    /** 是否耗材供应商 */
    private Integer isConsumable;

    /** 是否试剂供应商 */
    private Integer isReagent;



}
    