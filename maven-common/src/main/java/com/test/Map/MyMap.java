package com.test.Map;

public interface MyMap<K,V> {

    V put(K key,V value);

    V get(K key);

    int size();



    interface Entry<K,V>{
        //修改KV的值
        V setValue(V value);

        Entry<K,V> setNext(Entry<K,V> next);
    }

}
