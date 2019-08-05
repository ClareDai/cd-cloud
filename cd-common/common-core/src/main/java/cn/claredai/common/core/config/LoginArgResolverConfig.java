package cn.claredai.common.core.config;

import cn.claredai.common.core.feign.UserService;
import cn.claredai.common.core.resolver.ClientArgumentResolver;
import cn.claredai.common.core.resolver.TokenArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Description 公共配置类, 一些公共工具配置
 * @Author ClareDai
 * @Date create in 2019/6/2 0:02
 **/
public class LoginArgResolverConfig implements WebMvcConfigurer {
    @Lazy
    @Autowired
    private UserService userService;
    /**
     * Token参数解析
     *
     * @param argumentResolvers 解析类
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
        argumentResolvers.add(new TokenArgumentResolver(userService));
        //注入应用信息
        argumentResolvers.add(new ClientArgumentResolver());

    }
}
