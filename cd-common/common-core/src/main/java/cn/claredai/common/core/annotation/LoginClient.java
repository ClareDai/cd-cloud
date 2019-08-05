package cn.claredai.common.core.annotation;

import java.lang.annotation.*;

/**
 * @Description 请求的方法参数上添加该注解，则注入当前登录账号的应用id
 * 例：public void test(@LoginClient String clientId) //注入webApp
 * @Author ClareDai
 * @Date create in 2019/7/15 22:30
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginClient {
}
