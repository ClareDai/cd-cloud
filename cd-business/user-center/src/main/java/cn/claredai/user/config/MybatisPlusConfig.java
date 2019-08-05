package cn.claredai.user.config;

import cn.claredai.common.db.config.DefaultMybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/7/7 23:02
 **/
@Configuration
@MapperScan("cn.claredai.user.mapper")
public class MybatisPlusConfig extends DefaultMybatisPlusConfig {
}
