package fortec.mscm.base.feign.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CatalogVO
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/13 16:48
 */
@Data
public class CatalogVO implements Serializable {

    private String id;

    /** 商品类型 */
    private Integer materialTypeCode;

    /** 品类代码 */
    private String code;

    /** 品类名称 */
    private String name;

    /** 品类全称 */
    private String fullName;
}
