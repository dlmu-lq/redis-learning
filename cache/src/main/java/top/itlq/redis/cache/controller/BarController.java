package top.itlq.redis.cache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itlq.redis.cache.dao.BarEntity;
import top.itlq.redis.cache.service.BarService;

/**
 * @author liqiang
 * @description
 * @date 2020/8/16 下午5:52
 */
@RestController
@RequestMapping("bar")
public class BarController {
    @Autowired
    private BarService barService;

    @RequestMapping("get/{id}")
    public BarEntity get(@PathVariable("id") Long id){
        return barService.get(id);
    }
}
