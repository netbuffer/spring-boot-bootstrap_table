package cn.com.ttblog.spring_boot_bootstrap_table.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/redis")
public class RedisController {
    public RedisController() {
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/key", method = RequestMethod.GET)
    public Object getKey(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }
}