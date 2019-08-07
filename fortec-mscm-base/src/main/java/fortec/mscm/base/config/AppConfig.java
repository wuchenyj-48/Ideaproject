package fortec.mscm.base.config;

import fortec.common.core.annotation.EnableDefaultCoreConfig;
import fortec.common.feign.clients.UserClient;
import fortec.common.security.annotation.EnableDefaultSecurityCommonConfig;
import fortec.common.security.annotation.EnableDefaultSecurityResourceConfig;
import fortec.mscm.feign.annotation.EnableMscmFeignConfig;
import fortec.mscm.feign.clients.HospitalClient;
import fortec.mscm.feign.clients.SupplierClient;
import fortec.mscm.security.userdetails.MscmUserDetailsServiceImpl;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description: 程序配置信息
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/2 11:38
 * @Version: 1.0
 */
@Configuration
@ComponentScan({"fortec.mscm.base.service", "fortec.mscm.base.controller", "fortec.mscm.base.listener"})
@EnableDiscoveryClient
@EnableMscmFeignConfig
@EnableDefaultCoreConfig
@EnableDefaultSecurityCommonConfig
@EnableDefaultSecurityResourceConfig
public class AppConfig {

    @Primary
    @Bean
    public UserDetailsService mscmUserDetailsService(UserClient userClient, SupplierClient supplierClient, HospitalClient hospitalClient) {
        return new MscmUserDetailsServiceImpl(userClient, supplierClient,hospitalClient);
    }

}
