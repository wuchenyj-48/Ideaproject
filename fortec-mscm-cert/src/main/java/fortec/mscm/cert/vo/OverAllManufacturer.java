package fortec.mscm.cert.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName OverAllManufacturer
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/5 9:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class OverAllManufacturer implements Serializable {
    /**
     * 厂商id
     */
    private String manufacturerId;

    /**
     * 厂商名称
     */
    private String manufacturerName;

    /**
     * 院方要求资质
     */
    private Integer requireCnt;

    /**
     * 已上传资质
     */
    private Integer uploadedCnt;
}
