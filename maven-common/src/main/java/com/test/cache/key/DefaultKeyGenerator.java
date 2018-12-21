package com.test.cache.key;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class DefaultKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        Object obj = new DefaultKey(target, method, params);
        System.out.println("cache key--------------------"+obj.toString());
        return obj;
    }
}
