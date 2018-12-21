package com.test;

import com.test.service.ITryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testDemo {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ITryService service = (ITryService)context.getBean("service");
        System.out.println(service.getName());
    }
}
