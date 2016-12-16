package com.concurrent.study.java;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/12/16.
 */
public class MyBlockingQueue<T> {
    static class Node<T> {
        T item;
        Node<T> next;

        public Node(T item) {
            this.item = item;
        }
    }
    private int max_size;
    private int size;
    private Node<T> head;
    private Node<T> tail;
    private Lock lock = new ReentrantLock(true);
    private Condition isNull = lock.newCondition();
    private Condition isFull = lock.newCondition();
    public MyBlockingQueue(int size) {
        this.max_size = size;
    }

    private void insert(T value) {
        Node node = new Node(value);
        size++;
        if (head == null){
            head = node;
            tail = node;
            return;
        }
        tail.next = node;
        tail = node;
    }

    private T remove() {
        if (head == null){
            throw new NullPointerException();
        }
        Node node = head;
        head = node.next;
        node.next = null;
        size--;
        return (T) node.item;
    }

    public void put(T value) throws InterruptedException {
        lock.lock();
        try {
            while (size>=max_size){
                isFull.await();
            }
            insert(value);
            isNull.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        T value;
        lock.lock();
        try {
            while (size==0){
                isNull.await();
            }
            value = remove();
            isFull.signalAll();
        }finally {
            lock.unlock();
        }
        return value;
    }


}
