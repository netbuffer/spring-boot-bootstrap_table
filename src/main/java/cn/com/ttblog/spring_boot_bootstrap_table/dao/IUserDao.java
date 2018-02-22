package cn.com.ttblog.spring_boot_bootstrap_table.dao;

import cn.com.ttblog.spring_boot_bootstrap_table.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;

public interface IUserDao {

    User save(User user);

    User getUserById(@Param(value = "id") String userId);

    User findByName(String userName);

    List<User> getUserList(@Param("order") String order, @Param("limit") int limit, @Param("offset") int offset);

    List<User> getUserListQueryByName(@Param("search") String search, @Param("order") String order, @Param("limit") int limit, @Param("offset") int offset);

    long getUserListCount();

    long getUserListCount(String search);

    List<User> findTop5ByPhoneContaining(String phone, Sort sort);

    Page<User> findByNameContaining(String name, Pageable page);

    String querySex(@Param("id") String id);

    int getNewData();

    List getDataSum();

    User queryMaxUser();

    List<User> queryUserNameLike(@Param("name") String name);

    List<User> getUserByCardNo(@Param("no") String no);

    List<User> findByNameIgnoreCaseOrderByIdDesc(String name);

    List<User> findByNameLike(String name);

    List<User> findByNameLike(String name, Pageable pageable);

    @Async
    Future<User> findById(String id);

    User findByIdForUpdate(String id);

    User findByN(String name);

    User testJoin(String id);

    void delete(String id);
}