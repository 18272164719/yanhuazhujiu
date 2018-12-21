package com.test.session;

import com.test.binding.MappedProxy;
import com.test.config.Configuration;
import com.test.config.MappedStatement;
import com.test.executor.DefaultExecutor;
import com.test.executor.Executor;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 对外提供接口，把请求转发给Executor
 */
public class DefaultSqLSession implements SqlSession {

    private Configuration conf;

    private Executor executor;


    public DefaultSqLSession(Configuration conf) {
        this.conf = conf;
        executor = new DefaultExecutor(conf);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> selectList = selectList(statement,parameter);
        if(selectList == null || selectList.size() == 0){
            return null;
        }
        if(selectList.size() == 1){
            return selectList.get(0);
        }
        throw new RuntimeException("TOO MANY RESULT");
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement ms = conf.getMappedStatements().get(statement);
        return executor.query(ms,parameter);
    }

    //mapper没有实例，这里用动态代理来实现实例化
    @Override
    public <T> T getMapper(Class<T> type) {
        MappedProxy mp = new MappedProxy(this);
        return (T)Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type},mp);
    }
}
