package com.test.controller;

public class TestReflection {


    public static void main(String[] args)throws Exception {
        String name = "com.test.entity.User";
        Class clazz = Class.forName(name);
        System.out.println(clazz.getName());

        ClassLoader classLoader = TestReflection.class.getClassLoader();
        System.out.println(classLoader);

    }
}
