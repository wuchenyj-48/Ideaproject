package fortec.mscm.settlement.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import fortec.common.core.mybatis.handlers.DataEntityMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Description: https://www.jianshu.com/p/a80b88b7076b?utm_campaign=maleskine&utm_content=note&utm_medium=reader_share&utm_source=weixin
 * @Author: yuntao.zhou
 * @CreateDate: 2019/6/11 16:30
 * @Version: 1.0
 */
@Configuration
@MapperScan({"fortec.mscm.settlement.mapper"})
public class MybatisPlusConfig {


    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * SQL执行效率插件
     */
    @Bean
    @Profile({"dev", "test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new DataEntityMetaObjectHandler();
    }
}
