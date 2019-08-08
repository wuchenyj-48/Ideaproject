
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MaterialApplicantQueryRequest extends PageRequest {

    /** 医院ID */
    private String hospitalId;

    /** 供应商ID */
    private String supplierId;

    /** 单据号 */
    private String code;

    /** 单据状态 */
    private Integer status;

    private String hospitalName;

    private String supplierName;

}
    