package com.concurrent.study.java.SCMSW;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shuye on 2016/12/20.
 *
 * week1 2 2
 */
public class PhilosopherSyn extends Thread {
    private boolean eating;
    private PhilosopherSyn left;
    private PhilosopherSyn right;
    private Object table;
    private Random random;
    public PhilosopherSyn(Object table, String name){
        setName(name);
        eating =  false;
        this.table = table;
        random = new Random();
    }

    public void setLeft(PhilosopherSyn left) {
        this.left = left;
    }

    public void setRight(PhilosopherSyn right) {
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
        synchronized (table){
            System.out.println("Philosopher"+Thread.currentThread().getName()+" is thinking");
            eating = false;
            table.notify();
        }
        Thread.sleep(1000);
    }

    private void eat() throws InterruptedException {
        synchronized (table){
            while (left.eating||right.eating){
                table.wait();
            }
            System.out.println("Philosopher"+Thread.currentThread().getName()+" is eating");
            eating = true;
        }
        Thread.sleep(1000);
    }
    public static void main(String[] args) {
        ReentrantLock table = new ReentrantLock();
        PhilosopherSyn p1 = new PhilosopherSyn(table,"1");
        PhilosopherSyn p2 = new PhilosopherSyn(table,"2");
        PhilosopherSyn p3 = new PhilosopherSyn(table,"3");
        PhilosopherSyn p4 = new PhilosopherSyn(table,"4");
        PhilosopherSyn p5 = new PhilosopherSyn(table,"5");
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
