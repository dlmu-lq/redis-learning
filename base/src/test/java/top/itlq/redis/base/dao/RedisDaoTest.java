package top.itlq.redis.base.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.itlq.redis.base.dao.RedisDao;

@SpringBootTest
class RedisDaoTest {

    @Autowired
    RedisDao redisDao;

    @Test
    void testSet(){
        redisDao.set("a", "ccc");
        Assert.assertEquals(redisDao.get("a"), "ccc");
    }
}