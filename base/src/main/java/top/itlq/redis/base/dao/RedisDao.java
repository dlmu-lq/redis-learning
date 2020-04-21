package top.itlq.redis.base.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class RedisDao {

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    private final ReactiveValueOperations<byte[], byte[]> opsForValue;

    @Autowired
    public RedisDao(ReactiveStringRedisTemplate reactiveStringRedisTemplate){
        this.reactiveStringRedisTemplate = reactiveStringRedisTemplate;
        opsForValue = reactiveStringRedisTemplate.opsForValue(RedisSerializationContext.byteArray());
    }

    public Mono<Boolean> set(String key, String value){
        return set(key, value.getBytes(StandardCharsets.UTF_8));
    }

    public Mono<Boolean> set(String key, byte[] bytes){
        return opsForValue.set(key.getBytes(StandardCharsets.UTF_8), bytes);
    }

    public Mono<String> get(String key){
        return opsForValue.get(key.getBytes(StandardCharsets.UTF_8))
                .map(v->new String(v, StandardCharsets.UTF_8));
    }

    public Mono<Boolean> del(String key){
        return opsForValue.delete(key.getBytes(StandardCharsets.UTF_8));
    }

    public Flux<String> scan(String pattern){
        ScanOptions scanOptions = new ScanOptions.ScanOptionsBuilder()
                .count(0)
                .match(pattern)
                .build();
        return reactiveStringRedisTemplate.scan(scanOptions);
    }

    /**
     * not in production environment
     * @param pattern
     * @return
     */
    public Flux<String> keys(String pattern){
        return reactiveStringRedisTemplate.keys(pattern);
    }
}
