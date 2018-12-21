package com.test.dao;

import com.test.entity.Permission;
import com.test.entity.Resource;

import java.util.List;

public interface IPermissionDao extends BaseDao<Permission>{

    List<Resource> getPermissonByUser(Long userId);
}
