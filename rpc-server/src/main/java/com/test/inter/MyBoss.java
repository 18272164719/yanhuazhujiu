package com.test.inter;


public class MyBoss implements Boss{

    @Override
    public boolean payRise(int money) {
        System.out.println("我正在和客户谈需求！");
        if(money <= 5000){
            System.out.println("ok！可以加工资！");
            return true;
        }else{
            System.out.println("数额太大,等我回来再说!");
            return false;
        }
    }

    @Override
    public boolean riseWork() {
        System.out.println("我正在和客户谈需求！等我回来再说");
        return false;
    }
}
