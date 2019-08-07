package fortec.mscm.base.feign.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 医院信息
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/7 15:37
 * @Version: 1.0
 */
@Data
public class HospitalVO implements Serializable {

    private String id;

    /** 机构ID */
    private String officeId;

    /** 医院代码 */
    private String code;

    /** 医院名称 */
    private String name;

    /** 简称 */
    private String shortName;

    /** 拼音 */
    private String pinyin;

    /** 区域id */
    private Long regionId;

    /** 地址 */
    private String address;

    /** 联系人 */
    private String contactor;

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;
}
