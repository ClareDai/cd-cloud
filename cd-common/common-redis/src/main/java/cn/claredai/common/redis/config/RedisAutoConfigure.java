package cn.claredai.common.redis.config;

import cn.claredai.common.redis.properties.CacheManagerProperties;
import cn.claredai.common.redis.repository.RedisRepository;
import cn.claredai.common.redis.util.FastJson2JsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description redis 配置类
 * @Author ClareDai
 * @Date create in 2019/6/5 20:30
 **/
@EnableConfigurationProperties({RedisProperties.class, CacheManagerProperties.class})
@EnableCaching
public class RedisAutoConfigure {
    @Autowired
    private CacheManagerProperties cacheManagerProperties;

    @Bean
    public RedisSerializer<Object> fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<>(Object.class);
    }

    /**
     * RedisTemplate配置
     * @param factory
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Redis repository redis repository.
     *
     * @param redisTemplate the redis template
     * @return the redis repository
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisRepository redisRepository(RedisTemplate<String, Object> redisTemplate) {
        return new RedisRepository(redisTemplate);
    }

    @Bean(name = "cacheManager")
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration difConf = getDefConf().entryTtl(Duration.ofHours(1));

        //自定义的缓存过期时间配置
        int configSize = cacheManagerProperties.getConfigs() == null ? 0 : cacheManagerProperties.getConfigs().size();
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(configSize);
        if (configSize > 0) {
            cacheManagerProperties.getConfigs().forEach(e -> {
                RedisCacheConfiguration conf = getDefConf().entryTtl(Duration.ofSeconds(e.getSecond()));
                redisCacheConfigurationMap.put(e.getKey(), conf);
            });
        }

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(difConf)
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":" + method.getName() + ":");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    private RedisCacheConfiguration getDefConf() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .computePrefixWith(cacheName -> "cache".concat(":").concat(cacheName).concat(":"))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJson2JsonRedisSerializer()));
    }
}
