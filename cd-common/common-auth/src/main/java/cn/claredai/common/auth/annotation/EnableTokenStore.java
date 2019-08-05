package cn.claredai.common.auth.annotation;

import cn.claredai.common.auth.config.TokenStoreConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 开启token存储认证配置化
 * @Author ClareDai
 * @Date create in 2019/7/10 23:18
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TokenStoreConfig.class)
public @interface EnableTokenStore {
}
