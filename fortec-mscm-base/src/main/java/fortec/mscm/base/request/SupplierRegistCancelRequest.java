package fortec.mscm.base.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 供应商注册 取消审核请求信息
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/24 13:41
 * @Version: 1.0
 */
@Data
public class SupplierRegistCancelRequest implements Serializable {

    /** 取消原因 */
    private String reason;
}
