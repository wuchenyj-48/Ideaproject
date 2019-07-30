
package fortec.mscm.cert.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CertificateRepositoryQueryRequest extends PageRequest {

    /** 资质类型 */
    private String businessTypeCode;

    /** 资质ID */
    private String certificateId;

    /** 资质编号 */
    private String certificateNo;

    /** 目标id */
    private String targetDescribeId;

    /** 有效期 */
    private String beginExpiryDate;
    private String endExpiryDate;


    /** 资质名称 */
    private String certificateName;

}
    