package top.itlq.redis.cache.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import top.itlq.redis.cache.config.RedisKeyUtils;
import top.itlq.redis.cache.dao.Foo;
import top.itlq.redis.cache.dao.FooMapper;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个简单的将redis作为中间缓存，并使用锁在并发度高时保护数据库
 * @author liqiang
 */
@Slf4j
//@Service
public class FooService {

    @Autowired(required = false)
    private FooMapper fooMapper;
    @Autowired(required = false)
    private ObjectMapper objectMapper;
    @Autowired
    private StatefulRedisConnection<String, String> statefulRedisConnection;

    private ReactiveValueOperations<String, String> reactiveValueOperations;


    @Autowired
    public FooService(ReactiveStringRedisTemplate reactiveStringRedisTemplate){
        reactiveValueOperations = reactiveStringRedisTemplate.opsForValue();
    }

    // 仅保证单机安全
    private Lock fooLock = new ReentrantLock();

    public Foo getFoo(Long id){
        RedisCommands<String, String> redisCommands = statefulRedisConnection.sync();
        String redisId = RedisKeyUtils.foo(id);
        String cacheFoo = redisCommands.get(redisId);
        if(cacheFoo == null){
            while ((cacheFoo = redisCommands.get(redisId)) == null){
                try {
                    if(fooLock.tryLock(1, TimeUnit.SECONDS)){
                        try {
                            cacheFoo = redisCommands.get(redisId);
                            if(cacheFoo == null){
                                Foo foo = null;
                                cacheFoo = objectMapper.writeValueAsString(getFooFromDB(id));
                                log.info("get from db");
                                redisCommands.setex(redisId, 10,  cacheFoo);
                            }
                        } catch (JsonProcessingException e) {
                            log.error("序列化失败");
                        } finally {
                            fooLock.unlock();
                        }
                        break;
                    }
                } catch (InterruptedException e) {
                    log.error("尝试获取锁被打断");
                }
            }
        }
        try {
            return objectMapper.readValue(cacheFoo, Foo.class);
        } catch (JsonProcessingException e) {
            log.error("反序列化失败");
        }
        return null;
    }

    public Foo getFoo2(Long id){
        String redisId = RedisKeyUtils.foo(id);
        String cacheFoo = reactiveValueOperations.get(redisId).block();
        if(cacheFoo == null){
            while ((cacheFoo = reactiveValueOperations.get(redisId).block()) == null){
                try {
                    if(fooLock.tryLock(1, TimeUnit.SECONDS)){
                        try {
                            cacheFoo =  reactiveValueOperations.get(redisId).block();
                            if(cacheFoo == null){
                                cacheFoo = objectMapper.writeValueAsString(getFooFromDB(id));
                                log.info("get from db");
                                reactiveValueOperations.set(redisId, cacheFoo, Duration.ofSeconds(60)).block();
                            }
                        } catch (JsonProcessingException e) {
                            log.error("序列化失败");
                        } finally {
                            fooLock.unlock();
                        }
                        break;
                    }
                } catch (InterruptedException e) {
                    log.error("尝试获取锁被打断");
                }
            }
        }
        try {
            return objectMapper.readValue(cacheFoo, Foo.class);
        } catch (JsonProcessingException e) {
            log.error("反序列化失败");
        }
        return null;
    }

    public Foo getFooFromDB(long id){
        return fooMapper.load(id);
    }

}
