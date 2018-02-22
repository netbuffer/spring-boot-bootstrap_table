package cn.com.ttblog.spring_boot_bootstrap_table.dao.impl;

import cn.com.ttblog.spring_boot_bootstrap_table.dao.IUserDao;
import cn.com.ttblog.spring_boot_bootstrap_table.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

@Repository
public class UserDaoImpl implements IUserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public User save(User user) {
        mongoTemplate.save(user);
        return user;
    }

    @Override
    public User getUserById(String userId) {
        return mongoTemplate.findById(userId, User.class);
    }

    @Override
    public User findByName(String userName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(userName));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public List<User> getUserList(String order, int limit, int offset) {
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "adddate")));
        int skip = offset;
        query.skip(skip);
        query.limit(limit);
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> getUserListQueryByName(String search, String order, int limit, int offset) {
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "adddate")));
        query.addCriteria(Criteria.where("name").regex(search));
        int skip = offset;
        query.skip(skip);
        query.limit(limit);
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public long getUserListCount() {
        return mongoTemplate.count(null, User.class);
    }

    @Override
    public long getUserListCount(String search) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(search));
        return mongoTemplate.count(query, User.class);
    }

    @Override
    public List<User> findTop5ByPhoneContaining(String phone, Sort sort) {
        return null;
    }

    @Override
    public Page<User> findByNameContaining(String name, Pageable page) {
        return null;
    }

    @Override
    public String querySex(String id) {
        return null;
    }

    @Override
    public int getNewData() {
        return 0;
    }

    @Override
    public List getDataSum() {
        return null;
    }

    @Override
    public User queryMaxUser() {
        return null;
    }

    @Override
    public List<User> queryUserNameLike(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(".*?\\" + name + ".*"));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> getUserByCardNo(String no) {
        return null;
    }

    @Override
    public List<User> findByNameIgnoreCaseOrderByIdDesc(String name) {
        return null;
    }

    @Override
    public List<User> findByNameLike(String name) {
        return null;
    }

    @Override
    public List<User> findByNameLike(String name, Pageable pageable) {
        return null;
    }

    @Override
    public Future<User> findById(String id) {
        return null;
    }

    @Override
    public User findByIdForUpdate(String id) {
        return null;
    }

    @Override
    public User findByN(String name) {
        return null;
    }

    @Override
    public User testJoin(String id) {
        return null;
    }

    @Override
    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.findAndRemove(query, User.class);
    }
}