package top.itlq.redis.base.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RedisDao {

    private final ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate;

    @Autowired
    public RedisDao(ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate){
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    public Mono<Boolean> set(String key, String value){
        return reactiveRedisTemplate.opsForValue().set(key.getBytes(), value.getBytes());
    }

    public String get(String key){
        return reactiveRedisTemplate.opsForValue().get(key.getBytes())
                .map(v->new String((byte[])v)).block();
    }
}
