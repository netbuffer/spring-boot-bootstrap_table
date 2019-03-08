package cn.netbuffer;

import cn.netbuffer.spring_boot_bootstrap_table.model.User;
import cn.netbuffer.spring_boot_bootstrap_table.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootBootstrapTableApplicationTests {

	@Resource
	private IUserService userService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testFindByIdForUpdate(){
		userService.findByIdForUpdate(1L);
	}

	@Test
	public void test(){
		userService.getDataSum();
	}

	/**
	 * 添加user
	 */
	@Test
	public void testAdduser(){
		User user=new User();
		user.setName("tt");
		user.setAge(56);
		userService.addUser(user);
	}

}
