package cn.com.ttblog.spring_boot_bootstrap_table.service;

import cn.com.ttblog.spring_boot_bootstrap_table.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IUserService {
    User getUserById(String userId);

    User getUserByName(String userName);

    Page<User> getUserByName(String userName, Pageable page);

    void addUser(User user);

    List<User> getUserList(String order, int limit, int offset);

    //带有查询条件
    List<User> getUserList(String search, String order, int limit, int offset);

    long getUserListCount();

    long getUserListCount(String search);

    int getNewData();

    List<Map<String, Object>> getDataSum();

    void addUM();

    void addUMtest() throws IllegalArgumentException;

    void deleteById(String id);

    User findByIdForUpdate(String id);

    User updateUserStatus(String id, boolean isAdmin);
}