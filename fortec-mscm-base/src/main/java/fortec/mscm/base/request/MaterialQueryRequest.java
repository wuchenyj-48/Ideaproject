
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MaterialQueryRequest extends PageRequest {

    /** 商品类型 */
    private Integer materialTypeCode;

    /** 品名 */
    private String materialName;

    /** 商品名 */
    private String materialTradeName;

    /** 供应商标识 */
    private String supplierId;


}
    