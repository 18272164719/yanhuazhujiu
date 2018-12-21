package com.test.dao;

import com.shyl.common.entity.DataGrid;
import com.shyl.common.entity.PageParam;
import com.shyl.common.entity.Sort;
import org.hibernate.LockOptions;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T, ID extends Serializable> {

    T getById(ID var1);

    T get(ID var1, LockOptions var2);

    void lock(T var1, LockOptions var2);

    T save(T var1);

    void delete(T var1);

    T update(T var1);

    void saveOrUpdate(T var1);

    T persist(T var1);

    T merge(T var1);

    Long count(String var1, Object... var2);

    List<T> getAll(PageParam var1);

    List<T> limit(int var1, Sort var2);

    DataGrid<T> query(PageParam var1);

    T getByKey(PageParam var1);

    boolean isManaged(T var1);

    void clear();

    void flush();

    void evict(T var1);

    void refresh(T var1);

    void refresh(T var1, LockOptions var2);

    ID getIdentifier(T var1);

    int saveBatch(List<T> var1);

    int updateBatch(List<T> var1);

    int updateBatchWithInclude(List<T> var1, String... var2);

    int deleteBatch(List<T> var1);

    T saveJDBC(T var1);

    int updateJDBC(T var1);

    int deleteJDBC(T var1);
}
