package fortec.mscm.base.feign.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 供应商VO
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/7 9:11
 * @Version: 1.0
 */
@Data
public class SupplierVO implements Serializable {

    private String id;

    /** 机构ID */
    private String officeId;

    /** 识别码 */
    private String code;

    /** 统一社会信用代码 */
    private String companyCode;

    /** 供应商名称 */
    private String name;

    /** 助记码 */
    private String pinyin;

    /** 是否药品供应商 */
    private Integer isDrug;

    /** 是否耗材供应商 */
    private Integer isConsumable;

    /** 是否试剂供应商 */
    private Integer isReagent;

    /** 区域id */
    private Long regionId;

    /** 区域名称 */
    private String regionName;

    /** 地址 */
    private String address;

    /** 联系人 */
    private String contactor;

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;

    /** 移动电话 */
    private String mobile;
}
