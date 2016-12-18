package com.concurrent.study.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shuye on 2016/12/18.
 * SingleThreadExecutor单线程线程池，主要使用FIFO的阻塞队列将线程通过顺序运行
 */
public class SingleExecutor {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable subrun = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i<10; i++) {
                    System.out.println("sub Thread" + i);
                }
            }
        };
        Runnable mainrun = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i<10; i++) {
                    System.out.println("main Thread"+i);
                }
            }
        };
        for (int i = 0; i<5; i++) {
            executor.execute(subrun);
            executor.execute(mainrun);
        }
    }
}
