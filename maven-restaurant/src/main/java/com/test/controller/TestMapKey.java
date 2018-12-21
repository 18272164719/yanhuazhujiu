package com.test.controller;

import java.util.*;

public class TestMapKey {

    /**
     * map的多种遍历方式
     * @param args
     */
    public static void main(String[] args) {

        Map<String,String> map = new HashMap<>();
        for(int i = 0 ; i<= 1000000 ;i ++){
            map.put("key"+i,"value"+i);
        }

        /**
         * 第一种 普遍使用，二次取值
         */
        Long start = System.currentTimeMillis();
        for(String key : map.keySet()){
            String value = map.get(key);
        }
        Long end = System.currentTimeMillis();
        System.out.println("第一种" +(end.longValue()-start.longValue()));


        /**
         * 第二种 通过Map.entrySet使用iterator遍历key和value：
         */
        Long start1 = System.currentTimeMillis();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
        }
        Long end1 = System.currentTimeMillis();
        System.out.println("第二种"+(end1.longValue()-start1.longValue()));

        /**
         * 第三种  推荐，尤其是容量大时
         */
        Long start2 = System.currentTimeMillis();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
        }
        Long end2 = System.currentTimeMillis();
        System.out.println("第三种"+(end2.longValue()-start2.longValue()));


        /*map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));*/

        /**
         * 以下是Set的遍历
         */
        Set<String> set = new HashSet<>();
        for(int i = 0; i<= 1000; i++){
            set.add(i + "");
        }
        for(Iterator it = set.iterator(); it.hasNext() ;){
            System.out.println("set ---"+it.next());
        }
    }
}
