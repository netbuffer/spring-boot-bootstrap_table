package cn.com.ttblog;

import cn.com.ttblog.spring_boot_bootstrap_table.model.User;
import cn.com.ttblog.spring_boot_bootstrap_table.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootBootstrapTableApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootBootstrapTableApplicationTests.class);

    @Resource
    private IUserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testFindByIdForUpdate() {
        userService.findByIdForUpdate(1L);
    }

    @Test
    public void test() {
        userService.getDataSum();
    }

    /**
     * 添加user
     */
    @Test
    public void testAdduser() {
        User user = new User();
        user.setName("tt");
        user.setAge(56);
        userService.addUser(user);
    }

    /**
     * redis pipeline https://redis.io/topics/pipelining
     * 同时发送多条指令到server执行，避免了网络延迟带来的开销，效率高,
     */
    @Test
    public void testRedisPipeline() {
        LOGGER.info("开始执行");
        Object ret = stringRedisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (int i = 0; i < 1000; i++) {
                    redisConnection.incr("users_count".getBytes());
                }
                return null;
            }
        });
        LOGGER.info("结束执行:", ret);
    }

    @Test
    public void testRedisIncr() {
        LOGGER.info("开始执行");
        BoundValueOperations valueOperations = stringRedisTemplate.boundValueOps("users_count");
        for (int i = 0; i < 1000; i++) {
            valueOperations.increment(1);
        }
        LOGGER.info("结束执行");
    }

}
