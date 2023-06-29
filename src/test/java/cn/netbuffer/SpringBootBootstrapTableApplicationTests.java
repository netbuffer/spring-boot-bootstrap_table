package cn.netbuffer;

import cn.netbuffer.spring_boot_bootstrap_table.dao.IUserDao;
import cn.netbuffer.spring_boot_bootstrap_table.model.User;
import cn.netbuffer.spring_boot_bootstrap_table.service.IUserService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootBootstrapTableApplicationTests {

    @Resource
    private IUserService userService;
    @Resource
    private IUserDao userDao;

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

    @Test
    public void testFindAll() {
        int page = 1;
        int size = 1;
//        Sort sort = new Sort(Sort.Direction.DESC, "adddate");
//        PageRequest pageRequest = new PageRequest(page-1, size, sort);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<User> users = userDao.findAll(pageRequest);
        System.out.println("query users:{}" + ToStringBuilder.reflectionToString(users));
    }

}
