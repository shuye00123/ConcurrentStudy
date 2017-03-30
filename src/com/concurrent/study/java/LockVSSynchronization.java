package com.concurrent.study.java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shuye on 2017/3/30.
 */
public class LockVSSynchronization {
    private Lock lock = new ReentrantLock(false);
    private Integer i = 0;

    public class lockAndIncrement implements Runnable{

        @Override
        public void run() {
            lock.lock();
            try {
                for (int n = 0; n < 2500; n++){
                    i ++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
                System.out.println("Thread"+Thread.currentThread().getId()+" unlock!");
            }
        }
    }

    public void doLockIncrement() throws InterruptedException {
        Thread thread1 = new Thread(new lockAndIncrement());
        Thread thread2 = new Thread(new lockAndIncrement());
        Thread thread3 = new Thread(new lockAndIncrement());
        Thread thread4 = new Thread(new lockAndIncrement());
        Thread thread5 = new Thread(new lockAndIncrement());
        Thread thread6 = new Thread(new lockAndIncrement());
        Thread thread7 = new Thread(new lockAndIncrement());
        Thread thread8 = new Thread(new lockAndIncrement());
        long starttime = System.nanoTime();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        thread7.join();
        thread8.join();
        long endtime = System.nanoTime();
        System.out.printf("now i is %d, spend %d ns", i, endtime-starttime);

    }
    public static void main(String[] args) throws InterruptedException {
        LockVSSynchronization lockVSSynchronization = new LockVSSynchronization();
        lockVSSynchronization.doLockIncrement();
    }
}
