package com.test.dao.impl;

import com.shyl.common.entity.DataGrid;
import com.shyl.common.entity.PageParam;
import com.shyl.common.entity.Sort;
import com.test.dao.IBaseDao;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDao<T, ID extends Serializable> implements IBaseDao<T,ID>{

    private static Logger logger = LoggerFactory.getLogger(BaseDao.class);
    private SessionFactory sessionFactory;
    @Resource( name = "jdbc")
    private JdbcTemplate jdbc;
    protected Class<T> entityClass = (Class)((Class)((ParameterizedType)((ParameterizedType)this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    protected Session getNewSession() {
        return this.sessionFactory.openSession();
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T getById(ID id) {
        return this.get(id, (LockOptions)null);
    }

    public T get(ID id, LockOptions lockOptions) {
        return null;
    }

    @Override
    public void lock(T entity, LockOptions var2) {

    }

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public T update(T entity) {
        Assert.notNull(entity, "欲更新对象不能为空");
        this.getSession().update(entity);
        return entity;
    }

    @Override
    public void saveOrUpdate(T entity) {
        Assert.notNull(entity, "对象不能为空");
        this.getSession().saveOrUpdate(entity);
    }

    @Override
    public T persist(T entity) {
        return null;
    }

    @Override
    public T merge(T entity) {
        return null;
    }

    @Override
    public Long count(String entity, Object... var2) {
        return null;
    }

    @Override
    public List<T> getAll(PageParam entity) {
        return null;
    }

    @Override
    public List<T> limit(int entity, Sort var2) {
        return null;
    }

    @Override
    public DataGrid<T> query(PageParam entity) {
        return null;
    }

    @Override
    public T getByKey(PageParam entity) {
        return null;
    }

    @Override
    public boolean isManaged(T entity) {
        Assert.notNull(entity, "对象不能为空");

        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void flush() {

    }

    @Override
    public void evict(T entity) {

    }

    @Override
    public void refresh(T entity) {

    }

    @Override
    public void refresh(T entity, LockOptions var2) {

    }

    @Override
    public ID getIdentifier(T entity) {
        ClassMetadata meta = this.sessionFactory.getClassMetadata(this.entityClass);
        return null;
    }

    @Override
    public int saveBatch(List<T> entity) {
        return 0;
    }

    @Override
    public int updateBatch(List<T> entity) {
        return 0;
    }

    @Override
    public int updateBatchWithInclude(List<T> entity, String... var2) {
        return 0;
    }

    @Override
    public int deleteBatch(List<T> entity) {
        return 0;
    }

    @Override
    public T saveJDBC(T entity) {
        return null;
    }

    @Override
    public int updateJDBC(T entity) {
        return 0;
    }

    @Override
    public int deleteJDBC(T entity) {
        return 0;
    }
}
