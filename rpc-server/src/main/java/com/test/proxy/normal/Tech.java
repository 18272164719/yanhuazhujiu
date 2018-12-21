package com.test.proxy.normal;

import com.test.inter.Boss;
import com.test.inter.MyBoss;

public class Tech implements Boss{

    private Boss boss = new MyBoss();

    @Override
    public boolean payRise(int money) {
        return boss.payRise(money);
    }

    @Override
    public boolean riseWork() {
        return false;
    }
}
