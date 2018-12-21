package com.test.service;

import com.test.dao.IPermissionDao;
import com.test.entity.Permission;
import com.test.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionService extends BaseService<Permission> implements IPermissionService{
    @Autowired
    private IPermissionDao permissionDao;

    @Override
    public List<Resource> getPermissonByUser(Long userId) {
        return permissionDao.getPermissonByUser(userId);
    }
}
