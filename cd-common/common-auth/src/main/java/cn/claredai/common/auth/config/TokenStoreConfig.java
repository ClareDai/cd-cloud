package cn.claredai.common.auth.config;

import cn.claredai.common.auth.store.AuthDbTokenStore;
import cn.claredai.common.auth.store.AuthJwtTokenStore;
import cn.claredai.common.auth.store.AuthRedisTokenStore;
import cn.claredai.common.auth.store.ResJwtTokenStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Description token存储配置
 * @Author ClareDai
 * @Date create in 2019/6/19 15:36
 **/
@Configuration
public class TokenStoreConfig {
    @Configuration
    @ConditionalOnProperty(prefix = "cd.oauth2.token.store", name = "type", havingValue = "db")
    @Import(AuthDbTokenStore.class)
    public class JdbcTokenConfig {
    }

    @Configuration
    @ConditionalOnProperty(prefix = "cd.oauth2.token.store", name = "type", havingValue = "redis", matchIfMissing = true)
    @Import(AuthRedisTokenStore.class)
    public class RedisTokenConfig {
    }

    @Configuration
    @ConditionalOnProperty(prefix = "cd.oauth2.token.store", name = "type", havingValue = "authJwt")
    @Import(AuthJwtTokenStore.class)
    public class AuthJwtTokenConfig {
    }

    @Configuration
    @ConditionalOnProperty(prefix = "cd.oauth2.token.store", name = "type", havingValue = "resJwt")
    @Import(ResJwtTokenStore.class)
    public class ResJwtTokenConfig {
    }
}
