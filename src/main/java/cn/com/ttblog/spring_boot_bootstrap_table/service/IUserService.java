package cn.com.ttblog.spring_boot_bootstrap_table.service;

import java.util.List;
import java.util.Map;
import cn.com.ttblog.spring_boot_bootstrap_table.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
	public User getUserById(long userId);
	public User getUserByName(String userName);
	Page<User> getUserByName(String userName, Pageable page);
	public void addUser(User user);
	public List<User> getUserList(String order, int limit, int offset);
	//带有查询条件
	public List<User> getUserList(String search, String order, int limit, int offset);
	public long getUserListCount();
	public long getUserListCount(String search);
	public int getNewData();
	public List<Map<String, Object>> getDataSum();
	public void addUM();
	public void addUMtest() throws IllegalArgumentException;
	public void deleteById(Long id);
}