package cn.com.ttblog;

import cn.com.ttblog.spring_boot_bootstrap_table.model.User;
import cn.com.ttblog.spring_boot_bootstrap_table.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableMongoRepositories
public class SpringBootBootstrapTableApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootBootstrapTableApplicationTests.class);

    @Resource
    private IUserService userService;

    @Test
    public void testGetDataSum() {
        LOGGER.info("userService.getDataSum():{}",userService.getDataSum());
    }

    @Test
    public void testGetUserList() {
        LOGGER.info("getUserList:{}", userService.getUserList("adddate", 1, 0));
    }

}
