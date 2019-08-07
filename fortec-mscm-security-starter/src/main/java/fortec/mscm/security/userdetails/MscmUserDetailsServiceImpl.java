package fortec.mscm.security.userdetails;

import fortec.common.feign.clients.UserClient;
import fortec.common.feign.dto.UserInfoDTO;
import fortec.common.feign.model.CommonResult;
import fortec.common.security.userdetails.UserDetailsServiceImpl;
import fortec.mscm.base.feign.vo.SupplierVO;
import fortec.mscm.feign.clients.SupplierClient;
import lombok.extern.slf4j.Slf4j;
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

    public MscmUserDetailsServiceImpl(UserClient userClient, SupplierClient supplierClient) {
        super(userClient);
        this.userClient = userClient;
        this.supplierClient = supplierClient;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("load current user info....");

        CommonResult<UserInfoDTO> result = userClient.findByUserKey(username);

        if (!result.isSuccess()) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        UserInfoDTO userInfo = result.getData();

        UserInfoDTO.User user = userInfo.getUser();

        Set<String> authSet = new HashSet<>();
        String[] roles = userInfo.getRoles() == null ? new String[]{} : userInfo.getRoles();
        String[] permissions = userInfo.getPermissions() == null ? new String[]{} : userInfo.getPermissions();

        Arrays.asList(roles).forEach(r -> authSet.add("ROLE_" + r));
        Arrays.asList(permissions).forEach(p -> authSet.add(p));

        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(authSet.toArray(new String[0]));


        SupplierVO supplier = supplierClient.findByOfficeId(user.getOfficeId());

        return new ExtOAuthUser(user.getId(), username, user.getUserPassword(), user.getOfficeId(), supplier == null ? null : supplier, "", authorities);
    }
}
