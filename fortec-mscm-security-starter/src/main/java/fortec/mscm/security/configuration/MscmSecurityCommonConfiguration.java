package fortec.mscm.security.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import fortec.mscm.feign.clients.HospitalClient;
import fortec.mscm.feign.clients.SupplierClient;
import fortec.mscm.security.userdetails.ExtOAuthUser;
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

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        ParserConfig.getGlobalInstance().addAccept(ExtOAuthUser.class.getName());
        ParserConfig.getGlobalInstance().addAccept("fortec.common.upms.entity");
    }

    @Primary
    @Bean("mscmUserDetailsService")
    public MscmIntegrationUserDetailsService mscmUserDetailsService(SupplierClient supplierClient, HospitalClient hospitalClient) {

        return new MscmIntegrationUserDetailsService(supplierClient, hospitalClient);
    }


}
