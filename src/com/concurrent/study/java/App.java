package com.concurrent.study.java;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shuye on 2017/3/30.
 */
public class App {

//    private AtomicInteger count = new AtomicInteger(0);
    private volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        app.doWork();
    }

    public void doWork() throws InterruptedException {
        Thread t1 = new Thread(new Runnable(){
            public void run(){
                for(int i = 0;i < 10000;i++){
                    count++;
                }
            }
        });

        Thread t2 = new Thread(new Runnable(){
            public void run(){
                for(int i = 0;i < 10000;i++){
                    count++;
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Count is: " + count);
    }

}
