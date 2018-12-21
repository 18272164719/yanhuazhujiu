package com.test.mongodb;

import java.util.List;

public interface IMongoDao<T> {

    void save (T var,String tableName);

    void update(Long id,T var,String tableName);

    void delete(Long id,String tableName);

    T findById (Long id,Class clz,String tableName);

    List<T> findAll (Class clz,String tableName);
}
