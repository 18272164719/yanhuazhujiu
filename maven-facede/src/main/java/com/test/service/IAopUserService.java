package com.test.service;

import com.test.entity.User;

public interface IAopUserService extends IBaseService<User>{


    void addUser();

    void addResource();

    void doAll();

    void sendMsg();
}
