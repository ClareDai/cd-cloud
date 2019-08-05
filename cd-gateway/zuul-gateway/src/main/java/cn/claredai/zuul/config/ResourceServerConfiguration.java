package cn.claredai.zuul.config;

import cn.claredai.common.auth.annotation.EnableTokenStore;
import cn.claredai.common.auth.config.DefaultResourceServerConfig;
import cn.claredai.common.auth.properties.SecurityProperties;
import cn.claredai.common.core.config.DefaultPasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/7/15 22:07
 **/
@Configuration
@EnableResourceServer
@EnableConfigurationProperties(SecurityProperties.class)
@EnableTokenStore
@Import({DefaultPasswordConfig.class})
public class ResourceServerConfiguration extends DefaultResourceServerConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = setHttp(http)
                .authorizeRequests()
                .antMatchers(securityProperties.getAuth().getHttpUrls()).authenticated()
                .antMatchers(securityProperties.getIgnore().getUrls()).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest();
        setAuthenticate(authorizedUrl);
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers()
                .frameOptions()
                .disable()
                .and()
                .csrf().disable();
    }

    @Override
    public HttpSecurity setAuthenticate(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl) {
        return authorizedUrl.access("@permissionService.hasPermission(request, authentication)").and();
    }
}
