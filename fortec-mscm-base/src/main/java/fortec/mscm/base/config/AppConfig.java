package fortec.mscm.base.config;

import fortec.common.core.annotation.EnableDefaultCoreConfig;
import fortec.common.feign.annotation.EnableDefaultFeignConfig;
import fortec.common.feign.clients.UserClient;
import fortec.common.security.annotation.EnableDefaultSecurityCommonConfig;
import fortec.common.security.annotation.EnableDefaultSecurityResourceConfig;
import fortec.mscm.base.service.SupplierService;
import fortec.mscm.base.userdetails.BaseUserDetailsServiceImpl;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@EnableDefaultFeignConfig
@EnableDefaultCoreConfig
@EnableDefaultSecurityCommonConfig
@EnableDefaultSecurityResourceConfig
public class AppConfig {

    @Bean
    public UserDetailsService userDetailsService(UserClient userClient, SupplierService supplierService) {
        return new BaseUserDetailsServiceImpl(userClient, supplierService);
    }

}
