
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HospitalSupplierQueryRequest extends PageRequest {

    /** 医院ID */
    private String hospitalId;

    /** 供应商ID */
    private String supplierId;

    private String hospitalName;

    private String supplierName;

    private String keywords;


}
    