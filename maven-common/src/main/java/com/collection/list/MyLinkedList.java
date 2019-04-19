package com.collection.list;


import java.util.LinkedList;

public class MyLinkedList<E> {

    private Node<E> first;

    private Node<E> last;

    int size;

    public class Node<E> {
        Node<E> prev;
        E item;
        Node<E> next;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    public void add(E e) {
        final Node<E> l = last;
        //创建新的节点
        Node<E> node = new Node<E>(l, e, null);
        last = node;
        if (first == null) {
            first = node;
        } else {
            node.prev = l;
            l.next = node;
        }
        size++;
    }

    public void add(int index, E e) {
        checkElementIndex(index);

        Node<E> oldNode = getNode(index);
        Node<E> newNode = null;
        if(oldNode != null){
            //要移动节点的前一个节点
            Node<E> removePrevNode = oldNode.prev;
            if(removePrevNode == null){
                newNode = new Node<>(null,e,oldNode);
                first = newNode;
            }else{
                newNode = new Node<>(removePrevNode,e,oldNode);
                removePrevNode.next = newNode;
            }
            oldNode.prev = newNode;
            size++;
        }
    }

    public void remove(int index) {
        checkElementIndex(index);
        Node<E> removeNode = getNode(index);
        unlink(removeNode);

    }

    public E unlink(Node<E> removeNode) {
        final E element = removeNode.item;
        //要删除节点的前一个节点
        Node<E> removePrevNode = removeNode.prev;
        //要删除节点的后一个节点
        Node<E> removeNextNode = removeNode.next;
        if (removePrevNode == null) {
            first = removeNextNode;
        } else {
            removePrevNode.next = removeNextNode;
            removeNode.prev = null;
        }
        if (removeNextNode == null) {
            last = removePrevNode;
        } else {
            removeNextNode.prev = removePrevNode;
            removeNode.next = null;
        }
        removeNode.item = null;
        size--;
        return element;
    }

    public void remove(E e) {
        if (e == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (e.equals(node.item)) {
                    unlink(node);
                }
            }
        }
    }


    public E get(int index) {
        checkElementIndex(index);
        Node<E> node = getNode(index);
        return node.item;
    }

    public Node<E> getNode(int index) {
        Node<E> node = null;
        /*if (first != null) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }*/
        //高级写法
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException("越界啦!");
    }
}
