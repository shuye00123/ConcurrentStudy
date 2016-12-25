package com.concurrent.study.java.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuye on 2016/12/25.
 * FixThreadPool core线程与max线程数相同 任务队列是LinkBlockingQueue
 */
public class FixThreadPool {
    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i<10; i++) {
            final int a = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("thread " + a + "is running!");
                        TimeUnit.MILLISECONDS.sleep(1000);
                        System.out.println("thread " + a + "is stop!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executor.execute(runnable);
        }
        executor.shutdown();
    }
}
