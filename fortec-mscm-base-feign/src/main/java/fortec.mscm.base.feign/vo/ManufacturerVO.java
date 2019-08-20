package fortec.mscm.base.feign.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ManufacturerVO
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/13 15:38
 */
@Data
public class ManufacturerVO implements Serializable {

    private String id;

    /** 供应商ID */
    private String supplierId;

    /** 厂商名称 */
    private String name;

    /** 社会信用代码 */
    private String companyCode;

    /** 生产许可证 */
    private String productionLicence;

    /** 助记码 */
    private String pinyin;
}
