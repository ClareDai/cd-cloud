package cn.claredai.common.auth.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Description 认证服务器使用Redis存取令牌
 * 注意: 需要配置redis参数
 * @Author ClareDai
 * @Date create in 2019/6/24 14:17
 **/
public class AuthRedisTokenStore {
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public TokenStore tokenStore() {
        return new CustomRedisTokenStore(connectionFactory);
    }
}
