package fortec.mscm.security.userdetails;

import feign.FeignException;
import fortec.common.feign.clients.UserClient;
import fortec.common.security.userdetails.UserDetailsServiceImpl;
import fortec.common.upms.feign.vo.UserInfoVO;
import fortec.mscm.base.feign.vo.HospitalVO;
import fortec.mscm.base.feign.vo.SupplierVO;
import fortec.mscm.feign.clients.HospitalClient;
import fortec.mscm.feign.clients.SupplierClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/26 9:41
 * @Version: 1.0
 */
@Slf4j
public class MscmUserDetailsServiceImpl extends UserDetailsServiceImpl {

    private UserClient userClient;

    private SupplierClient supplierClient;

    private HospitalClient hospitalClient;

    public MscmUserDetailsServiceImpl(UserClient userClient, SupplierClient supplierClient,HospitalClient hospitalClient) {
        super(userClient);
        this.userClient = userClient;
        this.supplierClient = supplierClient;
        this.hospitalClient = hospitalClient;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("load current user info....");

        UserInfoVO infoVO = null;

        try {
            infoVO = userClient.findInfoByLoginKey(username);
        } catch (FeignException e) {
            e.printStackTrace();
            throw new InsufficientAuthenticationException("令牌已过期或用户服务内部错误");
        }

        if (infoVO == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        Set<String> authSet = new HashSet<>();
        String[] roles = infoVO.getRoles() == null ? new String[]{} : infoVO.getRoles();
        String[] permissions = infoVO.getPermissions() == null ? new String[]{} : infoVO.getPermissions();

        Arrays.asList(roles).forEach(r -> authSet.add("ROLE_" + r));
        Arrays.asList(permissions).forEach(p -> authSet.add(p));

        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(authSet.toArray(new String[0]));


        SupplierVO supplier = supplierClient.findByOfficeId(infoVO.getOfficeId());

        if(supplier != null){
            return new ExtOAuthUser(infoVO.getId(), username, infoVO.getPassword(), infoVO.getOfficeId(), supplier, null, authorities);
        }
        HospitalVO hospital = hospitalClient.findByOfficeId(infoVO.getOfficeId());
        return new ExtOAuthUser(infoVO.getId(), username, infoVO.getPassword(), infoVO.getOfficeId(), null ,hospital, authorities);
    }
}
