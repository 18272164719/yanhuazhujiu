package com.buffer;


import javax.annotation.processing.FilerException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestCompare {

    public static void main(String[] args) {
        /*List<User> users = new ArrayList<>();
        User u1 = new User("zhangsan",1);
        User u2 = new User("zhangsan1",7);
        User u3 = new User("zhangsan2",4);
        User u4 = new User("zhangsa3n",0);
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        Collections.sort(users);
        System.out.println(users);*/
        /*System.out.println("3.txt".compareTo("2.txt"));*/

    }
}


class User implements Comparable<User>{

    private String name ="" ;

    private Integer num = null;

    public User(String name,Integer num){
        this.name = name;
        this.num = num;
    }

    @Override
    public String toString() {
        return "name:"+this.name+"  ,num:"+this.num;
    }

    @Override
    public int compareTo(User o) {
        return this.num - o.getNum();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}

