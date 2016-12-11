package com.concurrent.study.java;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shuye on 2016/12/10.
 */
public class HashtableAndCHM {
    private static Hashtable<String, String> ht;
    private static ConcurrentHashMap<String, String> chm;

    static class htRunner implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i <1000000; i++) {
                ht.get("key" + i);
            }
        }
    }

    static class chmRunner implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i <1000000; i++) {
                chm.get("key"+i);
            }
        }
    }

    public static void main(String[] args) {
        ht = new Hashtable<String, String>();
        chm = new ConcurrentHashMap<String, String>();
        long t1 = System.nanoTime();
        for (int i = 0; i <1000000; i++) {
            ht.put("key"+i,"value"+i);
        }
        long t2 = System.nanoTime();
        for (int i = 0; i <1000000; i++) {
            chm.put("key"+i,"value"+i);
        }
        long t3 = System.nanoTime();
        for (int i = 0;i<1000000; i++){
            ht.get("key"+i);
        }
        long t4 = System.nanoTime();
        for (int i = 0;i<1000000; i++){
            chm.get("key" + i);
        }
        long t5 = System.nanoTime();
        System.out.println("hashtable put value spend "+ (t2 - t1));
        System.out.println("concurrrentHashMap put value spend "+ (t3 - t2));
        System.out.println("hashtable get value spend "+ (t4 - t3));
        System.out.println("concurrrentHashMap get value spend "+ (t5 - t4));
        long t6 = System.nanoTime();
        for (int i = 0; i<20;i++){
            Thread ht = new Thread(new htRunner());
            ht.start();
        }
        long t7 = System.nanoTime();
        for (int i = 0; i<20;i++){
            Thread chm = new Thread(new chmRunner());
            chm.start();
        }
        long t8 = System.nanoTime();
        System.out.println("20 thread get hashtable value spend "+ (t7 - t6));
        System.out.println("20 thread get concurrrentHashMap value spend "+ (t8 - t7));
        long t9 = System.nanoTime();
        for (int i = 0; i<200;i++){
            Thread ht = new Thread(new htRunner());
            ht.start();
        }
        long t10 = System.nanoTime();
        for (int i = 0; i<200;i++){
            Thread chm = new Thread(new chmRunner());
            chm.start();
        }
        long t11 = System.nanoTime();
        System.out.println("200 thread get hashtable value spend "+ (t10 - t9));
        System.out.println("200 thread get concurrrentHashMap value spend "+ (t11 - t10));
    }
}
