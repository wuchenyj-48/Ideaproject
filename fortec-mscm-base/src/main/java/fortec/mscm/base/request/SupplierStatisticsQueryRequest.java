package fortec.mscm.base.request;

import fortec.common.core.model.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author: chen.wu
 * @CreateDate: 2019-11-12 10:44
 * Version:      2.4
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SupplierStatisticsQueryRequest extends PageRequest {
     /** 统一社会信用代码 */
    private String code;

    /** 供应商名称 */
    private String name;

    /** 医院ID */
    private String hospitalId;

    /** 供应商ID */
    private String supplierId;

    /** 联系人 */
    private String contactor;

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;

    /** 状态 */
    private String inactive;

    private String hospitalName;



  
}
