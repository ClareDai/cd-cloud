package cn.claredai.auth.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @Description 校验码相关安全配置
 * @Author ClareDai
 * @Date create in 2019/6/24 14:10
 **/
@Component("validateCodeSecurityConfig")
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Resource
    private Filter validateCodeFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
