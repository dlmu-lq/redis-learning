package top.itlq.redis.base.dao;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import top.itlq.redis.base.dao.RedisDao;

import java.util.Arrays;

@SpringBootTest
class RedisDaoTest {

    @Autowired
    RedisDao redisDao;

    @BeforeEach
    void beforeEach(){
        String[] keys1 = {"ab", "ac", "ad"};
        String[] keys2 = {"ba", "bc"};
        byte[] values = {0};
        Assert.assertEquals(
                Flux.fromArray(keys1).concatWith(Flux.fromArray(keys2))
                        .flatMap(key -> redisDao.set(key, values))
                        .reduce(Boolean::logicalAnd).block(),
                true
        );
    }

    @Test
    void testSet(){
        String value = "测试";
        Assert.assertEquals(true, redisDao.set("a", value).block());
        Assert.assertEquals(redisDao.get("a").block(), value);
        Assert.assertEquals(true, redisDao.del("a").block());
    }

    @Test
    void testKeys(){
        String[] keys1 = {"ab", "ac", "ad"};
        Assert.assertTrue(
                redisDao.keys("a*")
                        .collectList().blockOptional()
                        .orElseThrow()
                        .containsAll(Arrays.asList(keys1))
        );
    }

    @Test
    void testScan(){
//        String[] keys1 = {"ab", "ac", "ad"};
//        String[] keys2 = {"ba", "bc"};
        System.out.println(
                redisDao.scan("a*").collectList().block()
        );
    }

    @AfterEach
    void afterEach(){
        String[] keys1 = {"ab", "ac", "ad"};
        String[] keys2 = {"ba", "bc"};
        byte[] values = {0};
        Assert.assertEquals(
                Flux.fromArray(keys1).concatWith(Flux.fromArray(keys2))
                        .flatMap(redisDao::del)
                        .reduce(Boolean::logicalAnd).block(),
                true
        );
    }

}