package com.test.service.impl;

import com.test.annotation.MyService;
import com.test.service.IEnjoyService;

@MyService
public class EnjoyService implements IEnjoyService{

    @Override
    public String query(String name, String age) {
        return "hello springmvc name----------"+name +"||age---------------"+age;
    }
}
