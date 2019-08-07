package fortec.mscm.feign.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description: 启用默认的Feign Client配置
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/23 9:22
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MscmFeignImportSelector.class)
public @interface EnableMscmFeignConfig {


}
