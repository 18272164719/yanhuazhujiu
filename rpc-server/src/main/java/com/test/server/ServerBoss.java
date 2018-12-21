package com.test.server;

import com.test.inter.Boss;
import com.test.inter.MyBoss;

public class ServerBoss {

    public static void main(String[] args) {
        Boss boss = new MyBoss();
        ServerSocketProxy serverSocketProxy = new ServerSocketProxy();

        //发噗端口
        serverSocketProxy.publish(boss.getClass(),10008);
    }
}
