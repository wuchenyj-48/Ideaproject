package fortec.mscm.feign.annotation;

import fortec.mscm.feign.configuration.MscmClientConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Description:
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/24 15:32
 * @Version: 1.0
 */
public class MscmFeignImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MscmClientConfiguration.class.getName()};
    }
}
