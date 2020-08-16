package top.itlq.redis.cache.config;

/**
 * 获得redis键的工具方法
 * @author liqiang
 */
public class RedisKeyUtils {
    public static String foo(Long id){
        return String.format("foo:%d", id);
    }
}
