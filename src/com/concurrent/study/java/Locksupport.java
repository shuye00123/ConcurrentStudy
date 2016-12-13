package com.concurrent.study.java;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by shuye on 2016/12/13.
 */
public class Locksupport {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println(Thread.currentThread().getName()+"Start working!");
                        Thread.sleep(100L);
                        System.out.println(Thread.currentThread().getName()+"Stop working!");
                        LockSupport.park();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        for (int i = 0; i<5; i++) {
            try {
                LockSupport.unpark(thread);
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
