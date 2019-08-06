package fortec.mscm.security.userdetails;

import fortec.common.core.userdetails.OAuthUser;
import fortec.common.core.utils.StringUtils;
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
    private String supplierId;

    /**
     * 医院ID
     */
    private String hospitalId;

    public ExtOAuthUser(String id,  String username, String password, String officeId, String supplierId, String hospitalId, Collection<? extends GrantedAuthority> authorities) {
        super(id,  username, password, officeId, authorities);
        this.supplierId = supplierId;
        this.hospitalId = hospitalId;
    }

    public ExtOAuthUser(OAuthUser oAuthUser, String supplierId, String hospitalId) {
        super(oAuthUser.getId(), oAuthUser.getUserKey(), oAuthUser.getPassword(), oAuthUser.getOfficeId(), oAuthUser.getAuthorities());
        this.supplierId = supplierId;
        this.hospitalId = hospitalId;
    }

    public ExtOAuthUser(String id, String username, String password, String officeId, String supplierId, String hospitalId, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(id,  username, password, officeId, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.supplierId = supplierId;
        this.hospitalId = hospitalId;
    }

    public boolean isSupplier() {
        return StringUtils.isNotBlank(supplierId);
    }

    public boolean isHospital() {
        return StringUtils.isNotBlank(hospitalId);
    }
}
