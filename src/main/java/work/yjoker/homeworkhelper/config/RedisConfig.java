package work.yjoker.homeworkhelper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author HeYunjia
 */

@Configuration
public class RedisConfig {

    @Resource
    RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        return redisTemplate;
    }

    @Bean
    public RedisCacheManager cacheManager() {

        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(180))
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(RedisSerializer.json()))
                .disableCachingNullValues();

        //根据redis缓存配置和reid连接工厂生成redis缓存管理器
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }
}
