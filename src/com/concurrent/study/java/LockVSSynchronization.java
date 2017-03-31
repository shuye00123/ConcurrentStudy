package com.concurrent.study.java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shuye on 2017/3/30.
 */
public class LockVSSynchronization {
    private Lock lock = new ReentrantLock(false);
    private Integer i = 0;

    /** java 1.8
     * now i is 20000, spend 4992756 ns t8
     * now i is 20000, spend 5043442 ns t10
     * now i is 200000, spend 30899516 ns t10
     * now i is 2000000, spend 113391942 ns t10
     * now i is 2400000, spend 119293765 ns t12
     * java 1.7
     * now i is 20000, spend 3795982 ns t8
     */

    private synchronized void doIncBySyn() {
        i++;
    }

    /** java 1.8
     * now i is 20000, spend 9220482 ns t8
     * now i is 20000, spend 9122756 ns t10
     * now i is 200000, spend 13841298 ns t10
     * now i is 2000000, spend 70595429 ns t10
     * now i is 2400000, spend 82051202 ns t12
     * java 1.7
     * now i is 20000, spend 4151879 ns t8
     */
    private void doIncByLock() {
        lock.lock();
        try {
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public class lockAndIncrement implements Runnable {

        @Override
        public void run() {
            for (int n = 0; n < 200000; n++) {
                doIncBySyn();
                //doIncByLock();
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
        Thread thread9 = new Thread(new lockAndIncrement());
        Thread thread10 = new Thread(new lockAndIncrement());
        Thread thread11 = new Thread(new lockAndIncrement());
        Thread thread12 = new Thread(new lockAndIncrement());
        long starttime = System.nanoTime();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
        thread11.start();
        thread12.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        thread7.join();
        thread8.join();
        thread9.join();
        thread10.join();
        thread11.join();
        thread12.join();
        long endtime = System.nanoTime();
        System.out.printf("now i is %d, spend %d ns", i, endtime - starttime);

    }

    public static void main(String[] args) throws InterruptedException {
        LockVSSynchronization lockVSSynchronization = new LockVSSynchronization();
        lockVSSynchronization.doLockIncrement();
    }
}
