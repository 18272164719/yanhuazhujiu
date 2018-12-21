package com.beauty;

import org.reflections.Reflections;

import java.util.concurrent.ConcurrentHashMap;

public class test {

    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException {

        //new 出来的对象脱离了spring容器，容器外，脱离了管理
        Context context = new Context();
        String s = context.reSum(2);
        System.out.println("结果：-----"+s);
    }
}
