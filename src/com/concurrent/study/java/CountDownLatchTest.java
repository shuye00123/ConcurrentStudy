package com.concurrent.study.java;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/16.
 */
public class CountDownLatchTest {
    static class Worker implements Runnable{
        private CountDownLatch countDownLatch;

        public Worker(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+" is running!");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        for (int i = 0; i<4; i++) {
            Thread thread = new Thread(new Worker(countDownLatch));
            thread.start();
        }
        countDownLatch.await();
        System.out.println("is all done!");
    }
}
