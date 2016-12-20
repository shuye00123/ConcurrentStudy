package com.concurrent.study.java.SCMSW;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shuye on 2016/12/20.
 *
 * week1 2 3
 */
public class ConcurrentSortList {
    private class Node{
        int value;
        Node prev;
        Node next;
        Node(){}
        Node(int value, Node prev, Node next){
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
    private final Node head;
    private final Node tail;
    private static Lock lock = new ReentrantLock();
    public ConcurrentSortList(){
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }
    public void insert(int value) {
        Node current = head;
        lock.lock();
        Node next = current.next;
        try {
            while (true){
                if (next == tail||next.value<value){
                    Node node = new Node(value, current, next);
                    next.prev = node;
                    current.next = node;
                    return;
                }
                current = next;
                next = current.next;
            }
        }finally {
            lock.unlock();
        }
    }

}
