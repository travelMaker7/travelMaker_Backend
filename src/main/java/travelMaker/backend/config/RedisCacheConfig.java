package travelMaker.backend.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    // RedisCacheManager가 아닌 CacheManager를 사용해도 OK
    // Spring Data Redis 를 사용한다면 Spring Boot 가 RedisCacheManager 를 자동으로 설정해준다.
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory rcf) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))  /*Value Serializer 변경*/
                .entryTtl(Duration.ofMinutes(30L)); // 캐시의 유효기간을 30분으로 설정

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(rcf)
                .cacheDefaults(redisCacheConfiguration).build();
    }
}
