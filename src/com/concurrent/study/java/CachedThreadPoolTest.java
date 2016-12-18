package com.concurrent.study.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuye on 2016/12/18.
 * CachedThreadPool corePoolSoze为0，maximumPoolSize为Integer.MAX_VALUE
 * 线程池中线程等待生存时间为60s
 */
public class CachedThreadPoolTest {
    static class Worker implements Runnable{

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
                System.out.println(Thread.currentThread().getName()+" is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i<10; i++){
            executorService.execute(new Worker());
        }
        TimeUnit.SECONDS.sleep(10);
        for (int i = 0; i<10; i++){
            executorService.execute(new Worker());
        }
        TimeUnit.SECONDS.sleep(60);
        for (int i = 0; i<10; i++){
            executorService.execute(new Worker());
        }
    }
}
