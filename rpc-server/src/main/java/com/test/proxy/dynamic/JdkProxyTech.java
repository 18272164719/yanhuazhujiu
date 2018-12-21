package com.test.proxy.dynamic;

import com.test.inter.Boss;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTech implements InvocationHandler{

    private Boss boss;

    public JdkProxyTech(Boss boss){
        this.boss = boss;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(boss,args);
    }


}
