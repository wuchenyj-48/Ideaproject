package fortec.mscm.security.userdetails;

import com.alibaba.fastjson.annotation.JSONField;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.userdetails.OAuthUser;
import fortec.mscm.base.feign.vo.HospitalVO;
import fortec.mscm.base.feign.vo.SupplierVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Description: 对默认的oauth 用户信息进行扩展，增加 医院、供应商等信息
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/26 9:49
 * @Version: 1.0
 */

@Data
public class ExtOAuthUser extends OAuthUser {

    /**
     * 供应商ID
     */
    private SupplierVO supplier;

    /**
     * 医院ID
     */
    private HospitalVO hospital;

    public ExtOAuthUser(String id, String username, String password, String officeId, SupplierVO supplier, HospitalVO hospital, Collection<? extends GrantedAuthority> authorities) {
        super(id, username, password, officeId, authorities);
        this.supplier = supplier;
        this.hospital = hospital;
    }

    public ExtOAuthUser(OAuthUser oAuthUser, SupplierVO supplier, HospitalVO hospital) {
        super(oAuthUser.getId(), oAuthUser.getLoginKey(), oAuthUser.getPassword(), oAuthUser.getOfficeId(), oAuthUser.getAuthorities());
        this.supplier = supplier;
        this.hospital = hospital;
    }

    public ExtOAuthUser(String id, String username, String password, String officeId, SupplierVO supplier, HospitalVO hospital, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(id, username, password, officeId, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.supplier = supplier;
        this.hospital = hospital;
    }

    @JSONField(serialize = false,deserialize = false)
    public boolean isSupplier() {
        return supplier != null;
    }

    @JSONField(serialize = false,deserialize = false)
    public boolean isHospital() {
        return hospital != null;
    }


    @JSONField(serialize = false,deserialize = false)
    public String getSupplierId() {
        if (!isSupplier()) {
            throw new BusinessException("当前用户非供应商身份，不允许操作");
        }
        return supplier.getId();
    }

    @JSONField(serialize = false,deserialize = false)
    public String getHospitalId() {
        if (!isHospital()) {
            throw new BusinessException("当前用户非医院身份，不允许操作");
        }
        return hospital.getId();
    }
}
