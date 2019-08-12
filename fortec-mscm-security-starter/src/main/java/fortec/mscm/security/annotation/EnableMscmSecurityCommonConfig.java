package fortec.mscm.security.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description: 启用MSCM中使用的通用安全配置
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/12 9:27
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MscmSecurityCommonImportSelector.class)
public @interface EnableMscmSecurityCommonConfig {


}
