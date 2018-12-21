package com.test.service.impl;


import com.shyl.common.entity.DataGrid;
import com.shyl.common.entity.PageParam;
import com.shyl.common.entity.Sort;
import com.test.dao.IBaseDao;
import com.test.service.IBaseService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BaseService<T, ID extends Serializable> implements IBaseService<T, ID> {

    private IBaseDao<T, ID> baseDao;

    public void setBaseDao(IBaseDao<T, ID> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    @Transactional
    public T save(T var1) {
        return baseDao.save(var1);
    }

    @Override
    @Transactional
    public void delete(T var1) {
        baseDao.delete(var1);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        baseDao.delete(this.baseDao.getById(id));
    }

    @Override
    @Transactional
    public T update(T var1) {
        return baseDao.update(var1);
    }

    @Override
    public T update(T entity, String... ignoreProperties) {
        Assert.notNull(entity);
        if(this.baseDao.isManaged(entity)){
            throw new IllegalArgumentException("Entity must not be managed");
        }else{
            T persistant = this.baseDao.getById(this.baseDao.getIdentifier(entity));
            if(persistant != null){
                this.copyProperties(entity, persistant, (String[])((String[])ArrayUtils.addAll(ignoreProperties, new String[]{})));
                return this.update(persistant);
            }else {
                return this.update(entity);
            }
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(T var1) {
        baseDao.saveOrUpdate(var1);
    }

    @Override
    @Transactional
    public T updateWithExclude(T var1, String... args) {
        T old = this.baseDao.getById(this.baseDao.getIdentifier(var1));
        if(old != null){
            try{
                String[] var8 = args;
                int var7 = args.length;
                for(int var6 = 0; var6 < var7; ++var6){
                    String arg = var8[var6];
                    PropertyUtils.setProperty(var1, arg, PropertyUtils.getProperty(old, arg));
                }
                PropertyUtils.copyProperties(old, var1);
            }catch (Exception e){
                e.printStackTrace();
            }
            return this.baseDao.update(var1);
        }else{
            return this.update(var1);
        }
    }

    @Override
    @Transactional
    public T updateWithInclude(T var1, String... var2) {



        return null;
    }

    @Override
    @Transactional
    public T merge(T var1) {
        return null;
    }

    @Override
    public T getById(ID var1) {
        return baseDao.getById(var1);
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public DataGrid<T> query(PageParam var1) {
        return null;
    }

    @Override
    public List<T> list(PageParam var1) {
        return null;
    }

    @Override
    public List<T> findList(ID... var1) {
        return null;
    }

    @Override
    public List<T> limit(int var1, Sort var2) {
        return null;
    }

    @Override
    public T getByKey(PageParam var1) {
        return null;
    }

    @Override
    @Transactional
    public int saveBatch(List<T> var2) {

        return 0;
    }

    @Override
    @Transactional
    public int updateBatch(List<T> var2) {
        return 0;
    }

    @Override
    @Transactional
    public int updateBatchWithInclude(List<T> var2, String... var3) {
        return 0;
    }


    private void copyProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
        List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        PropertyDescriptor[] var6 = targetPds;
        int var7 = targetPds.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            PropertyDescriptor targetPd = var6[var8];
            if (targetPd.getWriteMethod() != null && (ignoreProperties == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }

                        Object sourceValue = readMethod.invoke(source);
                        Object targetValue = readMethod.invoke(target);
                        if (sourceValue != null && targetValue != null && targetValue instanceof Collection) {
                            Collection collection = (Collection)targetValue;
                            collection.clear();
                            collection.addAll((Collection)sourceValue);
                        } else {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }

                            writeMethod.invoke(target, sourceValue);
                        }
                    } catch (Throwable var15) {
                        throw new FatalBeanException("Could not copy properties from source to target", var15);
                    }
                }
            }
        }

    }
}
