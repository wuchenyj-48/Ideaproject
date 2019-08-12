package fortec.mscm.security.annotation;

import fortec.mscm.security.configuration.MscmSecurityCommonConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Description:
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/12 9:28
 * @Version: 1.0
 */
public class MscmSecurityCommonImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MscmSecurityCommonConfiguration.class.getName()};
    }

}
