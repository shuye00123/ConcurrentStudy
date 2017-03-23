package com.concurrent.study.java;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by7/3/23.
 * atomic使用的是cas，
 * cas在Unsafe类，
 * 共有三种cas方法
 * public final native boolean compareAndSwapObject(Object var1, long var2, Object var4, Object var5)
 * public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5)
 * public final native boolean compareAndSwapLong(Object var1, long var2, long var4, long var6)
 * native说明是调用系统底层算法...
 */
public class Atomic {
    private static AtomicInteger i;

    public static class doIncrease implements Runnable{

        private AtomicInteger integer;

        public doIncrease(AtomicInteger integer) {
            this.integer = integer;
        }

        @Override
        public void run() {
            integer.getAndIncrement();
        }
    }

    public static void main(String[] args){
        i = new AtomicInteger(0);
        Thread t1 = new Thread(new doIncrease(i));
        Thread t2 = new Thread(new doIncrease(i));
        Thread t3 = new Thread(new doIncrease(i));
        t1.start();
        t2.start();
        t3.start();
        assert i.get() == 3;
        System.out.println("success");
    }
}
