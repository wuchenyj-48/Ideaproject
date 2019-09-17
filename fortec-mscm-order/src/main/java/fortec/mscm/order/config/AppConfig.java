package fortec.mscm.order.config;

import fortec.common.core.annotation.EnableDefaultCoreConfig;
import fortec.common.security.annotation.EnableDefaultSecurityCommonConfig;
import fortec.common.security.annotation.EnableDefaultSecurityResourceConfig;
import fortec.mscm.feign.annotation.EnableMscmFeignConfig;
import fortec.mscm.security.annotation.EnableMscmSecurityCommonConfig;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 程序配置信息
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/2 11:38
 * @Version: 1.0
 */
@Configuration
@ComponentScan({"fortec.mscm.order.service", "fortec.mscm.order.controller", "fortec.mscm.order.listener"})
@EnableDiscoveryClient
@EnableMscmFeignConfig
@EnableDefaultCoreConfig
@EnableMscmSecurityCommonConfig
@EnableDefaultSecurityCommonConfig
@EnableDefaultSecurityResourceConfig
public class AppConfig {



}
