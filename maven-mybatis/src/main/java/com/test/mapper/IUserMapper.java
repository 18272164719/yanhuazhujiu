package com.test.mapper;

import com.test.entity.User;

import java.util.List;

public interface IUserMapper {

    User getByKey(Long id);

    List<User> getAll();

}
