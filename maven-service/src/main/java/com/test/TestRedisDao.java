package com.test;

import com.test.entity.User;
import com.test.redis.IRedisDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRedisDao {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        IRedisDao redisDao = (IRedisDao) context.getBean("redisDao");

        User user = new User();
        user.setId(1L);
        user.setEmpId("admin");
        user.setName("小小");

        boolean b = redisDao.setNode("user", user);
        System.out.println("是否成功"+b);

        Object obj = redisDao.getNode("user");
        System.out.println((User)obj);
    }
}
