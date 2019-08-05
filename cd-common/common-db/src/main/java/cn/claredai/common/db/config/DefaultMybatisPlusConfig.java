package cn.claredai.common.db.config;

import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * @Description mybatis-plus配置
 * @Author ClareDai
 * @Date create in 2019/7/5 16:16
 **/
@Import(DateMetaObjectHandler.class)
public class DefaultMybatisPlusConfig {

    /**
     * 打印 sql，性能分析拦截器，不建议生产使用
     * 设置 dev test 环境开启
     */
    @Bean
    @Profile({"dev","test"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

}
