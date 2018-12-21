package com.test.Map;

public class TestHashMap {

    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>();
        map.put("a","a");
        map.put("a","abcd");
        map.put("b","b");
        map.put("c","c");
        map.put("d","d");
        System.out.println(map);
        System.out.println(map.get("a"));
    }
}
