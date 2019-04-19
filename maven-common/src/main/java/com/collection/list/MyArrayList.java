package com.collection.list;

import java.util.Arrays;

public class MyArrayList<E> {

    // 保存ArrayList中数据的数组
    private Object[] elementData;

    int size;

    public MyArrayList() {
        //默认初始化容量为10
        this(10);
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        // 初始化数组容量
        elementData = new Object[initialCapacity];
    }

    //添加方法
    public void add(E e) {
        ensureExplicitCapacity(size + 1);
        elementData[size++] = e;
    }

    //添加方法
    public void add(int index, E e) {
        rangeCheck(index);
        ensureExplicitCapacity(size + 1);
        int numMoved = elementData.length - index - 1;
        System.arraycopy(elementData, index++, elementData, index, numMoved);
        elementData[index] = e;
        size++;
    }

    public E remove(int index) {
        E e = get(index);
        rangeCheck(index);
        int numMoved = elementData.length - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
        return e;
    }

    public boolean remove(E e) {
        for (int i = 0; i < elementData.length; i++) {
            if (e.equals(elementData[i])) {
                remove(i);
            }
        }

        return false;
    }

    public void ensureExplicitCapacity(int minCapacity) {
        // 如果存入的数据,超出了默认数组初始容量 就开始实现扩容
        if (minCapacity == elementData.length) {
            // 获取原来数组的长度 2
            int oldCapacity = elementData.length;
            // oldCapacity >> 1 理解成 oldCapacity/2 新数组的长度是原来长度1.5倍
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    public E get(int index) {
        rangeCheck(index);
        return (E) elementData[index];
    }

    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("数组越界啦!");
        }
    }


}
