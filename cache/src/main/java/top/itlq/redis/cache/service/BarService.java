package top.itlq.redis.cache.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itlq.redis.cache.dao.BarEntity;

@Service
@Slf4j
@CacheConfig
public class BarService {
    @Cacheable(value = "bar") //Cacheable
    public BarEntity get(Long id){
        log.info("get from database");
        switch (id.intValue()){
            case 1:
                return new BarEntity(1L, "lc");
            case 2:
                return new BarEntity(2L, "lc");
            default:
                return null;
        }
    }
}
