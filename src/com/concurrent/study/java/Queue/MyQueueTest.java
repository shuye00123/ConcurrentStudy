package com.concurrent.study.java.Queue;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/16.
 */
public class MyQueueTest {
    public static void main(String[] args) throws InterruptedException {
        final MyBlockingQueue<String> queue = new MyBlockingQueue<>(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true){
                    try {
                        queue.put("test"+i++);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i>=10)break;
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        String taked = queue.take();
                        System.out.println(Thread.currentThread().getName()+taked);
                        if (taked.equals("test9"))break;
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
