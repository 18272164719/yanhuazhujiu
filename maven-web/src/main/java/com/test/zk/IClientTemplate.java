package com.test.zk;

public interface IClientTemplate {
    void createNode(String var1, Object var2);

    void updateNode(String var1, Object var2);

    void deleteNode(String var1);

    Object getNode(String var1);
}
