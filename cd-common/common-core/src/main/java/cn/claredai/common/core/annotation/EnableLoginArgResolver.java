package cn.claredai.common.core.annotation;

import cn.claredai.common.core.config.LoginArgResolverConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 在启动类上添加该注解来----开启自动登录用户对象注入
 * Token转化SysUser
 * @Author ClareDai
 * @Date create in 2019/6/2 0:01
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LoginArgResolverConfig.class)
public @interface EnableLoginArgResolver {
}
