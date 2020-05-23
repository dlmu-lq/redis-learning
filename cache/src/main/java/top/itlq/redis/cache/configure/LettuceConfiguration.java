package top.itlq.redis.cache.configure;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * todo 这里只配置单机，主从、哨兵、集群未配置测试
 * @author liqiang
 */
@EnableConfigurationProperties(RedisProperties.class)
@Configuration
public class LettuceConfiguration {
    @Bean(destroyMethod = "close")
    StatefulRedisConnection<String, String> statefulRedisConnection(RedisClient redisClient){
        return redisClient.connect();
    }

    @Bean(destroyMethod = "shutdown")
    RedisClient redisClient(RedisProperties redisProperties){
        return RedisClient.create(
                RedisURI.builder()
                        .withHost(redisProperties.getHost())
                        .withPort(redisProperties.getPort())
                        .withPassword(redisProperties.getPassword())
                        .build()
        );
    }
}
