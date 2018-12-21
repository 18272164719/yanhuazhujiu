package com.test.client;

import com.test.inter.Boss;

import java.lang.reflect.Proxy;

public class RpcClient {

    public <T>T getRomoteProxyObj(final Class<?> serviceInterface ,String host,int port){
        RomoteInvocationHandler rt = new RomoteInvocationHandler(serviceInterface, host, port);
        return (T)Proxy.newProxyInstance(serviceInterface.getClassLoader(),new Class<?>[]{serviceInterface},rt);
    }
}
