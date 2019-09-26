package fortec.mscm.settlement.config;

import fortec.common.core.annotation.EnableDefaultCoreConfig;
import fortec.common.security.annotation.EnableDefaultSecurityCommonConfig;
import fortec.common.security.annotation.EnableDefaultSecurityResourceConfig;
import fortec.mscm.feign.annotation.EnableMscmFeignConfig;
import fortec.mscm.security.annotation.EnableMscmSecurityCommonConfig;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: 程序配置信息
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/2 11:38
 * @Version: 1.0
 */
@Configuration
@ComponentScan({"fortec.mscm.settlement.service", "fortec.mscm.settlement.controller", "fortec.mscm.settlement.listener"})
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableMscmFeignConfig
@EnableDefaultCoreConfig
@EnableMscmSecurityCommonConfig
@EnableDefaultSecurityCommonConfig
@EnableDefaultSecurityResourceConfig
public class AppConfig {



}
