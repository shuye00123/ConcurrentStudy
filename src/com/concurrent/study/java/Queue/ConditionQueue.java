package com.concurrent.study.java.Queue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/12/9.
 */
public class ConditionQueue {
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    static boolean flag = false;
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName()+" get the state!");
                        while (!flag){
                            System.out.println(Thread.currentThread().getName()+" is waiting!");
                            condition.await();
                        }
                        System.out.println(Thread.currentThread().getName()+" get the state again!");
                        System.out.println(Thread.currentThread().getName()+" is pretend to work!");
                        TimeUnit.SECONDS.sleep(1);
                        flag = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        });
        t.start();
        while (true) {
            while (!flag){
                lock.lock();
                System.out.println(Thread.currentThread().getName()+" get the state!");
                try {
                    flag = true;
                    System.out.println(Thread.currentThread().getName()+" let all waitting queue node to state queue!");
                    condition.signalAll();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
}
