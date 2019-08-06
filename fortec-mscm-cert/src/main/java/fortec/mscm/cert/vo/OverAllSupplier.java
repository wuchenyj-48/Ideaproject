package fortec.mscm.cert.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/2 16:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class OverAllSupplier implements Serializable {

    /**
     * 供应商id
     */
    private String supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 院方要求资质
     */
    private Integer requireCnt;

    /**
     * 已上传资质
     */
    private Integer uploadedCnt;
}
