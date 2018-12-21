package com.beauty;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 工厂模式
 */
public class StrategyFactory {

    //饿汉式  没有线程 安全
    private static StrategyFactory strategyFactory = new StrategyFactory();

    //私有化构造器
    private StrategyFactory(){

    }

    private static Map<Integer,String> sourceMap = new HashMap<>();

    //map怎么取
    static{
        Reflections reflections = new Reflections("com.beauty.impl");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Pay.class);



        for(Class<?> clazz : classSet){
            Pay T = clazz.getAnnotation(Pay.class);
            sourceMap.put(T.channelId(),clazz.getCanonicalName());
        }
    }

    public Strategy create(int channelId) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String clazz = sourceMap.get(channelId);
        Class aClass =  Class.forName(clazz);
        return (Strategy)aClass.newInstance();
    }



    public static StrategyFactory getInstance(){
        return strategyFactory;
    }


}
