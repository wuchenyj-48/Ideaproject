package fortec.mscm.msg.config;

import fortec.mscm.feign.annotation.EnableMscmFeignConfig;
import fortec.mscm.security.annotation.EnableMscmSecurityCommonConfig;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/14 9:21
 * @Version: 1.0
 */
@Configuration
@EnableMscmFeignConfig
@EnableMscmSecurityCommonConfig
public class AppExtConfig {
}
