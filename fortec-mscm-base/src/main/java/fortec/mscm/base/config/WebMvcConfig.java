package fortec.mscm.base.config;

import com.alibaba.fastjson.serializer.DoubleSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import fortec.common.core.mvc.converter.StringTrimConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @CreateUser YunTao.Zhou
 * @CreateTime 2018/6/14 13:40
 * @ModifyUser YunTao.Zhou
 * @ModifiedTime 2018/6/14 13:40
 * @Version 1.0
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ResourceProperties resourceProperties;

    /**
     * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
     * <p>
     * <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
     * <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
     * </p>
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // 配置静态资源
        String[] staticLocations = resourceProperties.getStaticLocations();
        registry.addResourceHandler("/**")
                .addResourceLocations((staticLocations))
                .setCacheControl(CacheControl.noCache());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE").allowedHeaders("*");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.NotWriteRootClassName
        );

        // fastjson默认为科学计数法，现在使用保留小数后四位
        fastJsonConfig.getSerializeConfig().put(Double.class, new DoubleSerializer("#.######"));
        fastJsonConfig.getSerializeConfig().put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));

        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        /**
         * actuator 返回的 content-type 为 application/vnd.spring-boot.actuator.v2+json
         * 必须添加上此类型，否则会 406，相关信息参考：
         * {@link org.springframework.boot.actuate.endpoint.web.servlet.AbstractWebMvcEndpointHandlerMapping}
         * {@link org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration#MEDIA_TYPES}
         */
        fastMediaTypes.add(new MediaType("application", "*+json"));

        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(fastJsonHttpMessageConverter);
//
//        //处理字符串, 避免直接返回字符串的时候被添加了引号
//        StringHttpMessageConverter smc = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//        converters.add(smc);

    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }


    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 注册全局类型转换器
     */
    @PostConstruct
    public void addConversionConfig() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
            genericConversionService.addConverter(new StringTrimConverter());
        }
    }


}
