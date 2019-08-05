package cn.claredai.common.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Description spring获取bean工具类
 * @Author ClareDai
 * @Date create in 2019/6/24 23:49
 **/
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public static <T> T getBean(Class<T> cla) {
        return applicationContext.getBean(cla);
    }

    public static <T> T getBean(String name, Class<T> cal) {
        return applicationContext.getBean(name, cal);
    }

    public static String getProperty(String key) {
        return applicationContext.getBean(Environment.class).getProperty(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }
}
