package com.concurrent.study.java.Lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2016/12/8.
 */
public class RWLock {
    private static Map param;
    private volatile static int p = 0;
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);
    static class ReadTask implements Runnable{
        @Override
        public void run() {
            while (true) {
                lock.readLock().lock();
                try {
                    String get = String.valueOf(param.get(p));
                    if (get==null||get.equals("null"))break;
                    System.out.println("time "+p++);
                    System.out.println(Thread.currentThread().getName()+" get "+get);
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.readLock().unlock();
                }
            }
        }
    }
    static class WriteTask implements Runnable{
        @Override
        public void run() {
            int i = 0;
            while (true) {
                lock.writeLock().lock();
                try {
                    param.put(i++,System.currentTimeMillis());
                    System.out.println(Thread.currentThread().getName()+" put "+i);
                    TimeUnit.MILLISECONDS.sleep(10);
                    if (i == 100)break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.writeLock().unlock();
                }
            }
        }
    }
    public static void main(String[] args) {
        param = new HashMap<String, String>();
        Thread wt = new Thread(new WriteTask());
        wt.start();
        Thread rt1 = new Thread(new ReadTask());
        rt1.start();
        Thread rt2 = new Thread(new ReadTask());
        rt2.start();
    }
}

