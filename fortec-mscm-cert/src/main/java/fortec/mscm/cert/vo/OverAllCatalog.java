package fortec.mscm.cert.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName OverAllCatalog
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/5 10:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class OverAllCatalog implements Serializable {
    /**
     * 品类id
     */
    private String catalogId;

    /**
     * 品类名称
     */
    private String catalogName;

    /**
     * 院方要求资质
     */
    private Integer requireCnt;

    /**
     * 已上传资质
     */
    private Integer uploadedCnt;
}
