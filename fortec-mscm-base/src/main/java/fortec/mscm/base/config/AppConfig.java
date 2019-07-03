package fortec.mscm.base.config;

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
@ComponentScan({"fortec.mscm.base.service", "fortec.mscm.base.controller", "fortec.mscm.base.listener"})
@EnableDiscoveryClient
public class AppConfig {
}
