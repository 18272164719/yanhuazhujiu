package com.test.proxy.dynamic;

import com.test.inter.Boss;
import com.test.inter.MyBoss;

import java.lang.reflect.Proxy;

public class TestJdkProxyTech {

    public static void main(String[] args) {

        Boss proxy = (Boss) Proxy.newProxyInstance(JdkProxyTech.class.getClassLoader(),new Class[]{Boss.class},new JdkProxyTech(new MyBoss()));
        proxy.payRise(6000);
    }
}
