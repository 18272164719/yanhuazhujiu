package com.test.service;

import com.test.dao.IResourceDao;
import com.test.dao.IUserDao;
import com.test.entity.Resource;
import com.test.entity.User;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class AopUserService extends BaseService<User> implements IAopUserService{

    @javax.annotation.Resource
    private IUserDao userDao;

    @javax.annotation.Resource
    private IResourceDao resourceDao;

    @Override
    @Transactional
    public void addUser() {
        User user = new User();
        user.setEmpId("zhangsan");
        user.setPwd("123456");
        userDao.insert(user);
    }

    @Override
    @Transactional
    public void addResource() {
        Resource resource = new Resource();
        resource.setName("首页");
        resource.setType("京东");
        resourceDao.insert(resource);
    }

    @Override
    @Transactional
    public void doAll() {
        System.out.println(Thread.currentThread()+"进来了");
        addUser();
        addResource();
        IAopUserService currentProxy = (IAopUserService)AopContext.currentProxy();
        currentProxy.sendMsg();
    }

    @Override
    @Async
    public void sendMsg(){
        System.out.println(Thread.currentThread()+"发送消息成功！！！");
        throw new RuntimeException("超时");
    }
}
