package com.concurrent.study.java.SCMSW;

import java.util.Random;

/**
 * Created by shuye on 2016/12/19.
 * week1 2
 */
public class Philosopher extends Thread {
    static class Chopstick{
        private int id;

        public Chopstick(int id) {
            this.id = id;
        }
    }
    private Chopstick left, right;
    private Random random;
    public Philosopher(Chopstick left, Chopstick right, String name) {
        setName(name);
        this.left = left;
        this.right = right;
        random = new Random();
    }
    /**
     *5 10 40s
     *5 100 3min
     *5 1000 15min+
     *10 100  6min
     */
    public void run() {
        try{
            while (true) {
                System.out.println("philosopher "+Thread.currentThread().getName()+" is thinking.");
                Thread.sleep(random.nextInt(100));
                synchronized (left){
                    synchronized (right){
                        System.out.println("philosopher "+Thread.currentThread().getName()+" is eating.");
                        Thread.sleep(random.nextInt(100));
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Chopstick c1 = new Chopstick(1);
        Chopstick c2 = new Chopstick(2);
        Chopstick c3 = new Chopstick(3);
        Chopstick c4 = new Chopstick(4);
        Chopstick c5 = new Chopstick(5);
        Chopstick c6 = new Chopstick(6);
        Chopstick c7 = new Chopstick(7);
        Chopstick c8 = new Chopstick(8);
        Chopstick c9 = new Chopstick(9);
        Chopstick c10 = new Chopstick(10);
        Philosopher p1 = new Philosopher(c1, c2, "1");
        Philosopher p2 = new Philosopher(c2, c3, "2");
        Philosopher p3 = new Philosopher(c3, c4, "3");
        Philosopher p4 = new Philosopher(c4, c5, "4");
        Philosopher p5 = new Philosopher(c5, c6, "5");
        Philosopher p6 = new Philosopher(c6, c7, "6");
        Philosopher p7 = new Philosopher(c7, c8, "7");
        Philosopher p8 = new Philosopher(c8, c9, "8");
        Philosopher p9 = new Philosopher(c9, c10, "9");
        Philosopher p10 = new Philosopher(c10, c1, "10");
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        p6.start();
        p7.start();
        p8.start();
        p9.start();
        p10.start();
    }
}
