package com.test.service;

import com.github.pagehelper.PageHelper;
import com.test.dao.IUserDao;
import com.test.entity.User;
import com.test.framework.datasource.DynamicDataSourceHolder;
import com.test.mongodb.IMongoDao;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class UserService extends BaseService<User> implements IUserService {

    @Resource
    private IUserDao userDao;
    @Resource
    private IMongoDao mongoDao;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private TransactionTemplate transactionTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    @Cacheable(value = "user")
    public List<User> getByName(String name) {
        return userDao.getName(name);
    }

    public List<User> find(User user) {
        return userDao.find(user);
    }

    public User login(String empId, String pwd) {
        return userDao.login(empId, pwd);
    }

    @Override
    @Cacheable(value = "user")
    public List<User> listByAll(String projectCode) {
        logger.info("前置切点通知中 ---------" + DynamicDataSourceHolder.getDataSourceType());
        PageHelper.startPage(1, 3);
        /*List<User> list = mongoDao.findAll(User.class,"t_sys_user");
        if(!list.isEmpty()){
            return list;
        }*/
        return userDao.listByAll();
    }

    @Override
    @Cacheable(value = "user")
    public List<User> listByAll2(String projectCode) {
        logger.info("环绕通知中 ---------" + DynamicDataSourceHolder.getDataSourceType());
        PageHelper.startPage(1, 2);
        return userDao.listByAll();
    }

    /**
     * 目前分布式事务还存在一些问题，后续调整，可能要结合 mq
     *
     * @param projectCode
     * @param user
     * @return
     */
    @Override
    @CacheEvict(value = "user", allEntries = true)
    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", transactionContextEditor = DubboTransactionContextEditor.class)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public User save(String projectCode, User user) {
        userDao.insert(user);
        mongoDao.save(user,"t_sys_user");
        return userDao.getById(user.getId());
    }


    @Transactional
    public void confirmRecord(String projectCode, User user) {
        logger.info("confirmRecord方法进来了-------------" + user);
    }

    @Transactional
    public void cancelRecord(String projectCode, User user) {
        logger.info("cancelRecord方法进来了-------------" + user);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    /*@Transactional*/
    public User updateByAll(User user) {
        Boolean isVal = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                userDao.update(user);
                return true;
            }
        });
        return null;
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    @Transactional
    public void deleteAll(User user) {
        userDao.delete(user);
    }



}
