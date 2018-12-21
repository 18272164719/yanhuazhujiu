package com.test.service;

import java.util.List;

public interface IBaseService<T> {

    void insert (T t);

    void update (T t);

    void delete (T t);

    void saveBtach (List<T> t);

    void updateBatch (List<T> t);

    void deleteBatch (List<T> t);

    List<T> find(T o);

    T getById(Long id);

    int count(T o);
}
