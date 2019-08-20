package fortec.mscm.base.feign.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName MaterialVO
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/13 16:18
 */
@Data
public class MaterialVO implements Serializable {

    private String id;

    /** 供应商标识 */
    private String supplierId;

    /** 品类ID */
    private String catalogId;

    /** 商品类型 */
    private Integer materialTypeCode;

    /** 品名 */
    private String materialName;

    /** 商品名 */
    private String materialTradeName;

    /** ERP代码 */
    private String materialErpCode;

    /** 助记码 */
    private String pinyin;

    /** 注册证号 */
    private String certificateNo;

    /** 注册证效期 */
    private java.util.Date certificateExpiredDate;

    /** 批准文号 */
    private String approvalNo;

    /** 生产厂商 */
    private String manufacturerId;
}
