package fortec.mscm.base.config;

import fortec.common.core.properties.FilterIgnoreProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @Description: 资源服务器配置
 * @Author: yuntao.zhou
 * @CreateDate: 2019/6/14 15:35
 * @Version: 1.0
 */
@Configuration
@EnableResourceServer
@EnableOAuth2Client
public class OAuthConfig {


    /**
     * 资源服务器配置，参考：
     * https://docs.spring.io/spring-security-oauth2-boot/docs/current-SNAPSHOT/reference/htmlsingle/#oauth2-boot-authorization-server-spring-security-oauth2-resource-server
     */
    @EnableResourceServer
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @AllArgsConstructor
    protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {

        private final AuthenticationEntryPoint authenticationEntryPoint;

        private final AccessDeniedHandler accessDeniedHandler;

        private final TokenStore tokenStore;

        private final FilterIgnoreProperties ignoreProperties;


        private UserDetailsService userDetailsService;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

            /**
             * 配置userDetailsService，否则无法获取用户信息
             */
//            DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
//            userAuthenticationConverter.setUserDetailsService(userDetailsService);
//
//            DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
//            accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
//            tokenServices.setAccessTokenConverter(accessTokenConverter);

            resources.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
            .tokenStore(tokenStore)
//                    .tokenServices(tokenServices)
            ;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                    .csrf().disable()
                    .headers().frameOptions().disable()
                    .and()
                    .requestMatchers().anyRequest()
                    .and()
                    .anonymous()
                    .and()
                    .authorizeRequests();


            registry.antMatchers(HttpMethod.OPTIONS).permitAll();
            // 自定义过滤路径
            ignoreProperties.getUrls()
                    .forEach(url -> registry.antMatchers(url).permitAll());

//            registry.anyRequest().authenticated();
            registry.antMatchers("/**").authenticated();
            // @formatter:on
        }
    }


}
