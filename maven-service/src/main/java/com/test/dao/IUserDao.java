package com.test.dao;

import com.test.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IUserDao extends BaseDao<User>{

    User login (@Param("empId")String empId, @Param("pwd")String pwd);

    List<User> getName(String empId);

}
