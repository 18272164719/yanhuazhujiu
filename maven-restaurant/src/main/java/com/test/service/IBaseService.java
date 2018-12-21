package com.test.service;

import com.shyl.common.entity.DataGrid;
import com.shyl.common.entity.PageParam;
import com.shyl.common.entity.Sort;
import java.io.Serializable;
import java.util.List;

public interface IBaseService<T, ID extends Serializable>{
    T save(T var1);

    void delete (T var1);

    void delete(ID var1);

    T update (T var1);

    T update(T var1, String... var2);

    void saveOrUpdate(T var1);

    T updateWithExclude(T var1, String... var2);

    T updateWithInclude(T var1 ,String... var2);

    T merge(T var1);

    T getById(ID var1);

    Long count();

    DataGrid<T> query(PageParam var1);

    List<T> list(PageParam var1);

    List<T> findList(ID... var1);

    List<T> limit(int var1, Sort var2);

    T getByKey(PageParam var1);

    int saveBatch(List<T> var2);

    int updateBatch(List<T> var2);

    int updateBatchWithInclude(List<T> var2, String... var3);
}
