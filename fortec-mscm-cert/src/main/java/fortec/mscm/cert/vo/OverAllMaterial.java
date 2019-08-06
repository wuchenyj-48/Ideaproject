package fortec.mscm.cert.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName OverAllMaterial
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/5 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Accessors(chain = true)
public class OverAllMaterial implements Serializable {
    /**
     * 商品id
     */
    private String materialId;

    /**
     * 商品名称
     */
    private String materialName;

    /**
     * 院方要求资质
     */
    private Integer requireCnt;

    /**
     * 已上传资质
     */
    private Integer uploadedCnt;
}
