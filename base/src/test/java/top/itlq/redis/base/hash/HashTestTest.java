package top.itlq.redis.base.hash;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liqiang04
 * @description TODO
 * @date 2020/8/15 4:22 下午
 */
@SpringBootTest
class HashTestTest {

    @Autowired
    HashTest hashTest;

    @Test
    void saveUser() {
        User user = new User();
        user.setId(2L);
        user.setBirth(LocalDateTime.now());
        user.setCreateTime(Instant.now());
        user.setName("lq");
        hashTest.saveUser(user);
    }
}