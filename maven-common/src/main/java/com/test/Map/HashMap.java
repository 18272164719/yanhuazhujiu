package com.test.Map;

/**
 * 这里手写jdk1.7的 HashMap，主要是了解hashmap的存储方式
 * 链表形式  后来居上的存储结构
 * 线程不安全  红黑树  扩容
 */
public class HashMap<K, V> implements MyMap<K, V> {

    /**
     * 定义了一个存储对象的Entry对象的数组
     */
    Node<K, V>[] table = null;
    /**
     * 集合中元素的个数
     */
    private int size;
    /**
     * HashMap 数组的默认长度
     */
    private static int defaultCapacity = 16;
    /**
     * 默认加载因子
     */
    private static double defaultLoadFactory = 0.75;

    @Override
    public V put(K key, V value) {
        if (table == null) {
            table = new Node[this.defaultCapacity];
        }

        //1.通过hash算法确定插入的位置
        int index = getIndex(key, this.defaultCapacity);

        //判断是不是修改value，key已经存在的情况
        Node<K, V> node = table[index];
        while (node != null) {
            if (node.key.equals(key))
                return node.setValue(value);
            else
                node = node.next;
        }

        if (size >= this.defaultCapacity * this.defaultLoadFactory) {
            System.out.println("--------------开始扩容---------");
            resize();
        }

        //2.创建node对象，并且保存到数组上
        table[index] = new Node<K, V>(key, value, table[index]);
        this.size++;
        return value;
    }

    private void resize() {
        //this.defaultCapacity << 1   表示二进制左移一位  =  乘以2
        Node<K, V>[] newTable = new Node[this.defaultCapacity << 1];
        Node<K, V> node = null;
        for (int i = 0; i < table.length; i++) {
            node = table[i];
            while (node != null) {
                Node<K, V> temp = node.next;
                int index = getIndex(node.key, newTable.length);
                node.setNext(newTable[index]);
                newTable[index] = node;
                node = temp;
            }
        }
    }

    private int getIndex(Object key, int length) {
        if (key == null) {
            return 0;
        }
        //通过二进制的与算法
        int index = key.hashCode() & (length - 1);
        return index;
    }

    @Override
    public V get(K key) {
        //1.通过hash算法确定插入的位置
        int index = getIndex(key, this.defaultCapacity);

        //判断是不是修改value，key已经存在的情况
        Node<K, V> node = table[index];
        while (node != null) {
            if (node.key.equals(key))
                return node.value;
            else
                node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return 0;
    }


    static class Node<K, V> implements Entry<K, V> {

        K key;  //键
        V value; //值
        Node<K, V> next;  //下一个node

        public Node(K key, V value, Node<K, V> next) {
            super();
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public Entry<K, V> setNext(Entry<K, V> next) {
            Node<K, V> oldNext = this.next;
            this.next = (Node<K, V>) next;
            return oldNext;
        }
    }
}
