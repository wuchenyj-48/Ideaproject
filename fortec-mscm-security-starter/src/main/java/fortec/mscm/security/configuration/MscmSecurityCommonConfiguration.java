package fortec.mscm.security.configuration;

import fortec.common.feign.clients.UserClient;
import fortec.mscm.feign.clients.HospitalClient;
import fortec.mscm.feign.clients.SupplierClient;
import fortec.mscm.security.userdetails.MscmUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description:
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/12 9:46
 * @Version: 1.0
 */
@Configuration
public class MscmSecurityCommonConfiguration {


    @Primary
    @Bean("mscmUserDetailsService")
    public UserDetailsService mscmUserDetailsService(UserClient userClient, SupplierClient supplierClient, HospitalClient hospitalClient) {
        return new MscmUserDetailsServiceImpl(userClient, supplierClient, hospitalClient);
    }

}
