package fortec.mscm.security.userdetails;

import fortec.common.feign.clients.UserClient;
import fortec.common.feign.dto.UserInfoDTO;
import fortec.common.feign.model.CommonResult;
import fortec.common.security.userdetails.UserDetailsServiceImpl;
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

//    private SupplierService supplierService;

    public MscmUserDetailsServiceImpl(UserClient userClient) {
        super(userClient);
        this.userClient = userClient;
//        this.supplierService = supplierService;
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


//        Supplier supplier = supplierService.getOne(Wrappers.<Supplier>query().eq("office_id", user.getOfficeId()));

//        return new ExtOAuthUser(user.getId(), username, user.getUserPassword(), user.getOfficeId(), supplier == null ? null : supplier.getId(), "", authorities);
        return new ExtOAuthUser(user.getId(), username, user.getUserPassword(), user.getOfficeId(), null, "", authorities);
    }
}
