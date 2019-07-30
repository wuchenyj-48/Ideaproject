
package fortec.mscm.cert.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CertificateQueryRequest extends PageRequest {

    /** 资质编码 */
    private String code;

    /** 资质名称 */
    private String name;

    /** 需要有效期 */
    private Integer needExpiryDate;

    /** 搜索关键词 */
    private String keywords;

}
    