package cn.com.ttblog.spring_boot_bootstrap_table.controller;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cache/test")
public class SpringCacheTestController {

    private Map data = new HashMap();
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String USER_CACHE_STR = "userCache";

    @Cacheable(value = USER_CACHE_STR, key = "#root.methodName+'-user-'+#userId")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Map getUser(Long userId) {
        log.info("get user[{}]", userId);
        data.put("id", userId);
        data.put("time", DateTime.now().toString(FORMAT));
        return data;
    }

    @CachePut(value = USER_CACHE_STR, key = "'getUser-user-'+#userId")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Map updateUser(Long userId) {
        log.info("update user[{}]", userId);
        data.put("id", userId);
        data.put("time", DateTime.now().toString(FORMAT));
        return data;
    }

    @CacheEvict(value = USER_CACHE_STR, key = "#key")
    @RequestMapping(method = RequestMethod.GET, value = "/evict")
    public @ResponseBody
    String evict(String key) {
        return USER_CACHE_STR + ":" + key;
    }
}
