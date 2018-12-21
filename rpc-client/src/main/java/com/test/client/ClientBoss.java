package com.test.client;

import com.test.inter.Boss;

public class ClientBoss {

    public static void main(String[] args) {

        RpcClient rpcClient = new RpcClient();
        Boss boss = rpcClient.getRomoteProxyObj(Boss.class, "127.0.0.1", 10008);
        boss.riseWork();
    }
}
