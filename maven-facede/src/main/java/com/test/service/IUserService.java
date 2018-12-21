package com.test.service;

import com.test.annotation.ProjectCodeFlag;
import com.test.entity.User;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.TransactionContext;

import java.util.List;

public interface IUserService extends IBaseService<User> {

    User login (String empId,String pwd);

    List<User> listByAll(@ProjectCodeFlag  String projectCode);

    List<User> listByAll2(@ProjectCodeFlag String projectCode);

    @Compensable
    User save (@ProjectCodeFlag String projectCode, User user);

    User updateByAll (User user);

    void deleteAll (User user);

    List<User> getByName(String name);
}
