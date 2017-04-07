package com.concurrent.study.java;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shuye on 2017/3/30.
 * volatile increment is not thread-safe
 * and if use volatile variable with single operation, it's thread-safe
 */
public class App {

//    private AtomicInteger count = new AtomicInteger(0);
    private volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        //app.doIncrement();
        app.doSignature();
    }

    public void doIncrement() throws InterruptedException {
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

    public void doSignature() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                while (true){
                    if (count == 0){
                        System.out.printf("this is t1, now count is 0, and now is %d turn\n", i);
                        count = 1;
                        i++;
                    }
                    if (i == 100){
                        break;
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                while (true){
                    if (count == 1){
                        System.out.printf("this is t2, now count is 1, and now is %d turn\n", i);
                        count = 0;
                        i++;
                    }
                    if (i == 100){
                        break;
                    }
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("done!");
    }

}
