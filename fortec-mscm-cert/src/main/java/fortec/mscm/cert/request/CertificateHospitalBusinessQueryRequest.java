
package fortec.mscm.cert.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CertificateHospitalBusinessQueryRequest extends PageRequest {

    /** 院方ID */
    private String hospitalId;

    /** 业务编码 */
    private String businessTypeCode;

    /** 资质ID */
    private String certificateId;

    /** 院方名称 */
    private String hospitalName;

    /** 资质名称 */
    private String certificateName;

    /** 资质编码 */
    private String code;


}
    