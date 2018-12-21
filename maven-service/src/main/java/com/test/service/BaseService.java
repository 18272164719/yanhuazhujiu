package com.test.service;

import com.test.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseService<T> implements IBaseService<T>{

    @Autowired
    private BaseDao<T> baseDao;

    @Override
    public void insert(T t) {
        baseDao.insert(t);
    }

    @Override
    public void update(T t) {
        baseDao.update(t);
    }

    @Override
    public void delete(T t) {
        baseDao.delete(t);
    }

    @Override
    public void saveBtach(List<T> t) {
        baseDao.saveBtach(t);
    }

    @Override
    public void updateBatch(List<T> t) {
        baseDao.updateBatch(t);
    }

    @Override
    public void deleteBatch(List<T> t) {
        baseDao.deleteBatch(t);
    }

    @Override
    public List<T> find(T o) {
        return baseDao.find(o);
    }

    @Override
    public T getById(Long id) {
        return baseDao.getById(id);
    }

    @Override
    public int count(T o) {
        return baseDao.count(o);
    }
}
