
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierRegistQueryRequest extends PageRequest {

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

    /** 审核状态 : 0：未提交，1：待审核，2：审核通过。字典类型：base_supplier_regist_astatus */
    private Integer astatus;

}
    