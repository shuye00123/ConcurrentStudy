package com.concurrent.study.java.SCMSW;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shuye on 2016/12/20.
 *
 * week1 2 1
 *
 *   公平锁是FIFO队列，适合严格控制执行顺序的情况，非公平锁是抢占式的，吞吐量更大，有可能有的进程一直获取不到锁
 *   ReentrantReadWriteLock是读写锁，其中的WriteLock是与ReentrantLock相似的独占锁，而ReadLock是共享锁，他们共用一个队列，一个状态码  其中高16位是共享锁，低16位是独占锁，适用于读多于写而且读不改变值的情况
 *   虚假唤醒是await的线程非条件下被唤醒，判断条件用while不用if可以避免虚假唤醒
 *   AtomicIntegerFielfUpdater可以用一个Object和一个Integer属性名初始化，为对象的那个属性进行原子操作
 *
 *
 *
 *   读取多的情况用交替锁，少不用
 */
public class PhilosopherCondition extends Thread {
    private boolean eating;
    private PhilosopherCondition left;
    private PhilosopherCondition right;
    private ReentrantLock table;
    private Condition condition;
    private Random random;
    public PhilosopherCondition(ReentrantLock table, String name) {
        setName(name);
        eating =  false;
        this.table = table;
        condition = table.newCondition();
        random = new Random();
    }
    public void setLeft(PhilosopherCondition left){
        this.left = left;
    }

    public void setRight(PhilosopherCondition right) {
        this.right = right;
    }

    public void run() {
        try {
            while (true){
                think();
                eat();
            }
        }catch (InterruptedException e){

        }
    }

    private void think() throws InterruptedException {
        table.lock();
        try {
            System.out.println("Philosopher"+Thread.currentThread().getName()+" is thinking");
            eating = false;
            left.condition.signal();
            right.condition.signal();
        }finally {
            table.unlock();
        }
        Thread.sleep(1000);
    }

    private void eat() throws InterruptedException {
        table.lock();
        try {
            while (left.eating||right.eating){
                condition.await();
            }
            System.out.println("Philosopher"+Thread.currentThread().getName()+" is eating");
            eating = true;
        }finally {
            table.unlock();
        }
        Thread.sleep(1000);
    }

    public static void main(String[] args) {
        ReentrantLock table = new ReentrantLock();
        PhilosopherCondition p1 = new PhilosopherCondition(table,"1");
        PhilosopherCondition p2 = new PhilosopherCondition(table,"2");
        PhilosopherCondition p3 = new PhilosopherCondition(table,"3");
        PhilosopherCondition p4 = new PhilosopherCondition(table,"4");
        PhilosopherCondition p5 = new PhilosopherCondition(table,"5");
        p1.setLeft(p5);
        p1.setRight(p2);
        p2.setLeft(p1);
        p2.setRight(p3);
        p3.setLeft(p2);
        p3.setRight(p4);
        p4.setLeft(p3);
        p4.setRight(p5);
        p5.setLeft(p4);
        p5.setRight(p1);
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
    }
}
