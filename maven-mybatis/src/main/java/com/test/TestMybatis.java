package com.test;

import com.test.entity.User;
import com.test.mapper.IUserMapper;
import com.test.session.DefaultSqLSession;
import com.test.session.SqlSession;
import com.test.session.SqlSessionFactory;

import java.util.List;

public class TestMybatis {

    //测试mybatis框架
    public static void main(String[] args) {

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();

        SqlSession session = sqlSessionFactory.openSession();

        IUserMapper mapper = session.getMapper(IUserMapper.class);

        List<User> users = mapper.getAll();
        for(User user : users){
            System.out.println(user);
        }
    }



}
