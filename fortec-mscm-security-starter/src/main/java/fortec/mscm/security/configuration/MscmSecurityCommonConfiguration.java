package fortec.mscm.security.configuration;

import fortec.mscm.feign.clients.HospitalClient;
import fortec.mscm.feign.clients.SupplierClient;
import fortec.mscm.security.userdetails.MscmIntegrationUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

/**
 * @Description:
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/12 9:46
 * @Version: 1.0
 */
@Configuration
@Order(-100)
public class MscmSecurityCommonConfiguration {


    @Primary
    @Bean("mscmUserDetailsService")
    public MscmIntegrationUserDetailsService mscmUserDetailsService( SupplierClient supplierClient, HospitalClient hospitalClient) {
        return new MscmIntegrationUserDetailsService( supplierClient, hospitalClient);
    }

}
