package top.itlq.redis.base.hash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.stereotype.Component;

/**
 * @author liqiang
 * @description 测试redis存储hash，时间类型
 * @date 2020/8/15 4:13 下午
 */
@Component
public class HashTest {

    /**
     * 用户信息redis key
     */
    private static final String USER_INFO_KEY_FORMAT = "user:%d";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HashMapper<Object, String, Object> hashMapper;

    public boolean saveUser(User user){
        redisTemplate.opsForHash().putAll(getUserInfoKey(user.getId()), hashMapper.toHash(user));
        return true;
    }


    private String getUserInfoKey(Long userId){
        return String.format(USER_INFO_KEY_FORMAT, userId);
    }

}
