package com.test.service;

import com.test.entity.Permission;
import com.test.entity.Resource;

import java.util.List;

public interface IPermissionService extends IBaseService<Permission>{

    List<Resource> getPermissonByUser (Long userId);
}
