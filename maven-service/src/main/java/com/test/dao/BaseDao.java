package com.test.dao;

import java.util.List;

public interface BaseDao<T> {

    void insert (T t);

    void update (T t);

    void delete (T t);

    void saveBtach (List<T> t);

    void updateBatch (List<T> t);

    void deleteBatch (List<T> t);

    T getById(Long id);

    List<T> find(T o);

    int count(T o);

    List<T> listByAll();
}
