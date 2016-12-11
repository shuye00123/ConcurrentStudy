package com.concurrent.study.java;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shuye on 2016/12/10.
 */
public class CopyOnWrite {
    private static Lock lock = new ReentrantLock();
    static class CowRunner implements Runnable {
        private int method;
        private CopyOnWriteArrayList list;

        public CowRunner(int method, CopyOnWriteArrayList list) {
            this.method = method;
            this.list = list;
        }

        @Override
        public void run() {
            if (method == 1) {
                Iterator<String> iterator =list.iterator();
                StringBuilder str = new StringBuilder();
                while (iterator.hasNext()){
                    str.append(iterator.next());
                }
                System.out.println(Thread.currentThread().getName()+" "+str.toString());
            }else {
                list.add(Thread.currentThread().getName() + "add ");
            }
        }
    }

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
        for (int i = 0; i<10; i++) {
            Thread wthread = new Thread(new CowRunner(0, list));
            wthread.start();
            Thread rthread = new Thread(new CowRunner(1, list));
            rthread.start();
        }
    }
}
