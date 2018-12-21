package com.test.controller;

import org.I0Itec.zkclient.ZkClient;

public class testZookeeper {

    public static void main(String[] args) {
        ZkClient zk = new ZkClient("192.168.11.200");
        String node = "/TEST_SESSIONS/d047f28d-f95e-429e-99c2-df51ffc204df";
        /*if(!zk.exists(node)){
            zk.createPersistent(node,testController.class);
        }
        if(zk.exists(node)){
            System.out.println("节点创建成功");
        }*/
        System.out.println("该节点存在  "+zk.exists(node));
    }
}
