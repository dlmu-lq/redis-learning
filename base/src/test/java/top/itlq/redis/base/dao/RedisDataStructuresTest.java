package top.itlq.redis.base.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RedisDataStructuresTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void testIntSet(){
        redisTemplate.opsForSet().add("numbers", 6);
    }

    @Test
    void testHash(){
//        redisTemplate.opsForHash().put("liang", "age", 18);
        Object x = redisTemplate.opsForHash().get("liang", "age");
        System.out.println(x.getClass());
        System.out.println(x);

        // 不是这样存进去（这样存进去的其实是"\"lc\""），使用命令行存储的字符串反序列化失败
        redisTemplate.opsForHash().put("liang", "name", "lc");
        Object x1 = redisTemplate.opsForHash().get("liang", "name");
        System.out.println(x1.getClass());
        System.out.println(x1);

        Map<String, String> details = new HashMap<>();
        details.put("hobby", "北奥");
        redisTemplate.opsForHash().put("liang", "details", details);
        Object o = redisTemplate.opsForHash().get("liang", "details");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Test
    void testSet(){

    }
}
