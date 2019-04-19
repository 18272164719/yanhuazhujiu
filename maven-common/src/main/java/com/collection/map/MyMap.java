package com.collection.map;

public interface MyMap<K, V> {

    V put(K key, V value);

    V get(K key);

    int size();

    interface Entry<K, V> {

        V setValue(V value);

        Entry<K, V> setNext(Entry<K, V> next);
    }
}
