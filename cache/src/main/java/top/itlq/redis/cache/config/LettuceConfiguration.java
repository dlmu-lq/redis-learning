package top.itlq.redis.cache.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.val;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

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
        val builder = RedisURI.builder()
                .withHost(redisProperties.getHost())
                .withPort(redisProperties.getPort());
        if(Objects.nonNull(redisProperties.getPassword())){
            builder.withPassword(redisProperties.getPassword());
        }
        return RedisClient.create(builder.build());
    }
}
