package com.concurrent.study.java;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuye on 2016/12/18.
 * 信号量
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i<5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread() + "is already");
                        semaphore.acquire();
                        System.out.println(Thread.currentThread() + "is running");
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }

                }
            }).start();
        }
        System.out.println("main thread end");
    }
}
