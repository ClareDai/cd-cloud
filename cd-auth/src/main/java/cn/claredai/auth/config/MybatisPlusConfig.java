package cn.claredai.auth.config;

import cn.claredai.common.db.config.DefaultMybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/7/5 23:41
 **/
@Configuration
@MapperScan("cn.claredai.auth.mapper")
public class MybatisPlusConfig extends DefaultMybatisPlusConfig {
}
