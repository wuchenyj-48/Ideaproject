
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

    /** 医院名称 */
    private String hospitalName;

    /** 供应商名称 */
    private String supplierName;

    /** 关键词搜索 */
    private String keywords;

    /** 停用标志 */
    private String inactive;


}
    