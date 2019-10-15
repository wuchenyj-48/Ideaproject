
package fortec.mscm.settlement.request;

import fortec.common.core.model.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StockQueryRequest extends PageRequest {

    /** 医院id */
    private String hospitalId;

    /** 供应商id */
    private String supplierId;

    /** 医院名称 */
    private String hospitalName;

    /** 供应商名称 */
    private String supplierName;

    /** 仓库名称 */
    private String warehouseName;

    /** 品名 */
    private String materialName;

    /** 品规 */
    private String materialSpec;

    /** 单价(元) */
    private Double beginPrice;
    private Double endPrice;

    /** 效期 */
    private java.util.Date beginExpiredDate;
    private java.util.Date endExpiredDate;


}
    