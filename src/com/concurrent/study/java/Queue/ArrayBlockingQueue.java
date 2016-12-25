package com.concurrent.study.java.Queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuye on 2016/12/11.
 */
public class ArrayBlockingQueue {
    enum WorkUnit{
        RUN,
        STOP
    }
    static class Worker implements Runnable{
        BlockingQueue<WorkUnit> in;
        BlockingQueue<WorkUnit> out;

        public Worker(BlockingQueue<WorkUnit> in, BlockingQueue<WorkUnit> out) {
            this.in = in;
            this.out = out;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    WorkUnit w = in.take();
                    if (w == WorkUnit.RUN) {
                        for (int i = 0; i<10; i++) {
                            System.out.println("sub thread run"+i);
                            TimeUnit.MICROSECONDS.sleep(1);
                        }
                    }
                    out.put(w);
                    if (w == WorkUnit.STOP)break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        BlockingQueue<WorkUnit> in = new java.util.concurrent.ArrayBlockingQueue<>(1);
        BlockingQueue<WorkUnit> out = new java.util.concurrent.ArrayBlockingQueue<>(1);
        Thread thread = new Thread(new Worker(in ,out));
        thread.start();
        try {
            for (int i = 0; i<5;i++) {
                System.out.println(i);

                in.put(WorkUnit.RUN);
                out.take();
                for (int j = 0; j< 10; j++) {
                    System.out.println("main thread" + j);
                    TimeUnit.MICROSECONDS.sleep(1);
                }
            }
            in.put(WorkUnit.STOP);
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
