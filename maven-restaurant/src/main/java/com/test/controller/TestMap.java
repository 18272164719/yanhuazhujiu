package com.test.controller;

import com.test.entity.User;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMap {
    public enum Type{
        A,
        B,
        C
    }
    private int num;
    private Type type;
    public TestMap(Type type,int num){
        this.type = type;
        this.num = num;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
}

class test {
    public static void main(String[] args) {
        TestMap t1 = new TestMap(TestMap.Type.A,1);
        TestMap t2 = new TestMap(TestMap.Type.A,2);
        TestMap t3 = new TestMap(TestMap.Type.A,3);

        TestMap t4 = new TestMap(TestMap.Type.B,1);
        TestMap t5 = new TestMap(TestMap.Type.B,2);
        TestMap t6 = new TestMap(TestMap.Type.B,3);

        TestMap t7 = new TestMap(TestMap.Type.C,1);
        TestMap t8 = new TestMap(TestMap.Type.C,2);
        TestMap t9 = new TestMap(TestMap.Type.C,3);
        List<TestMap> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        list.add(t5);
        list.add(t6);
        list.add(t7);
        list.add(t8);
        list.add(t9);
        Map<TestMap.Type,TestMap> map = new HashMap<>();
        for(TestMap testMap : list){
            TestMap t = map.get(testMap.getType());
            if(t != null){
                if(t.getNum() - testMap.getNum() > 0){
                    map.put(testMap.getType(),testMap);
                }
            }else{
                map.put(testMap.getType(),testMap);
            }
        }
        //System.out.println(map.values());
        for(TestMap t : map.values()){
            System.out.println(t.getType()+"-----"+t.getNum());
        }
    }

}
