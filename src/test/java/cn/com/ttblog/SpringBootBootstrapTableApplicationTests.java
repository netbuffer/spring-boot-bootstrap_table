package cn.com.ttblog;

import cn.com.ttblog.spring_boot_bootstrap_table.dao.IUserDao;
import cn.com.ttblog.spring_boot_bootstrap_table.model.User;
import cn.com.ttblog.spring_boot_bootstrap_table.service.IUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
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
    @Resource
    private IUserDao userDao;

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setName(RandomStringUtils.random(10, true, true));
        user.setAge(RandomUtils.nextInt(10, 100));
        user.setAdddate((int)(System.currentTimeMillis() / 1000));
        user.setSex("n");
        user.setDeliveryaddress(RandomStringUtils.random(20, true, true));
        LOGGER.info("save user:{}", userDao.save(user));
    }

    @Test
    public void testGetDataSum() {
        LOGGER.info("userDao.getDataSum():{}", userDao.getDataSum());
    }

    @Test
    public void testGetUserList() {
        LOGGER.info("getUserList:{}", userService.getUserList("adddate", 1, 0));
    }

}
