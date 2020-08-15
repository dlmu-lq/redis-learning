package top.itlq.redis.base.hash;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;

/**
 * @author liqiang
 * @description
 * @date 2020/8/15 4:36 下午
 */
@Configuration
public class HashMapperConfig {
    @Bean
    public HashMapper<Object, String, Object> hashMapper(){
        /// Jackson2HashMapper构造器会自动注册合适的jackson module进行转化，且设置合适的属性
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
        return new Jackson2HashMapper(false);
    }
}
