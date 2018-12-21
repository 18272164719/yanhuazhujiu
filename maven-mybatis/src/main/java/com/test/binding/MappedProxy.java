package com.test.binding;

import com.test.config.Configuration;
import com.test.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

//动态代理类
public class MappedProxy implements InvocationHandler{

    private SqlSession session;

    public MappedProxy(SqlSession session) {
        this.session = session;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        if(Collection.class.isAssignableFrom(returnType)){
            return session.selectList(method.getDeclaringClass().getName()+"."+method.getName(),args==null?null:args[0]);
        }
        return session.selectOne(method.getDeclaringClass().getName()+"."+method.getName(),args==null?null:args[0]);
    }
}
