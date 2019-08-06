
package fortec.mscm.cert.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CertificateRepositoryHistoryQueryRequest extends PageRequest {

    /** 资质仓库ID */
    private String certificateRepositoryId;

    /** 资质类型 */
    private String businessTypeCode;


}
    