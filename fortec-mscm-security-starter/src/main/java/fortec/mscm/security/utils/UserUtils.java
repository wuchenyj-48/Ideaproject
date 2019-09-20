package fortec.mscm.security.utils;

import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.utils.SecurityUtils;
import fortec.mscm.base.feign.vo.HospitalVO;
import fortec.mscm.base.feign.vo.SupplierVO;
import fortec.mscm.security.userdetails.ExtOAuthUser;

/**
 * @Description:
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/7 10:36
 * @Version: 1.0
 */
public class UserUtils {

    /**
     * 获取当前用户信息
     * @return
     */
    public static ExtOAuthUser getUser(){
        return (ExtOAuthUser) SecurityUtils.getCurrentUser();
    }

    /**
     * 获取医院ID
     * @return
     */
    public static String getHospitalId(){
        return getUser().getHospitalId();
    }

    /**
     * 获取供应商ID
     * @return
     */
    public static String getSupplierId(){
        return getUser().getSupplierId();
    }


    public static HospitalVO getHospital(){
        if(!getUser().isHospital()){
            throw new BusinessException("当前非医院身份，不允许操作");
        }
        return getUser().getHospital();
    }

    public static SupplierVO getSupplier(){
        if(!getUser().isSupplier()){
            throw new BusinessException("当前非供应商身份，不允许操作");
        }
        return getUser().getSupplier();
    }

}
