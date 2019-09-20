
package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HospitalMaterialQueryRequest extends PageRequest {

    /** 医院标识 */
    private String hospitalId;

    /** 供应商标识 */
    private String supplierId;

    /** 商品名称 */
    private String materialName;

    /** 价格 */
    private Long beginPrice;
    private Long endPrice;

    /** 停用标志 */
    private Integer inactive;

    /**医院名称*/
    private String hospitalName;

    /**商品规格*/
    private String materialSpec;

    /**商品价格*/
    private Double price;

    /** 关键字 */
    private String keywords;

}
    