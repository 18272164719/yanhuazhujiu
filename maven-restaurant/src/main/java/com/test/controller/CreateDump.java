package com.test.controller;

import java.util.ArrayList;
import java.util.List;

public class CreateDump {


    public static void main(String[] args) {
        List list = new ArrayList();
        int i = 0;
        while(true){
            list.add(String.valueOf(i++).intern());
        }
    }
}
