package com.collection.map;

import java.util.HashMap;
import java.util.Map;

public class TestMap {

    public static void main(String[] args) {
        MyHashMap myHashMap = new MyHashMap();

        for(int i = 0 ;i < 20 ;i++){
            myHashMap.put(i,i);
        }
        System.out.println(myHashMap.get("a"));

    }
}
