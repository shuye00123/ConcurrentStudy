package com.concurrent.study.java.ForkJoin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by shuye on 2016/12/25.
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i<10; i++) {
            subTask task = new subTask();
            FutureTask<String> futureTask1 = new FutureTask<String>(task);
            new Thread(futureTask1).start();
            System.out.println(futureTask1.get());
            mainRunner runner = new mainRunner();
            FutureTask<String> futureTask2 = new FutureTask<String>(runner, "main thread is finished");
            new Thread(futureTask2).start();
            System.out.println(futureTask2.get());
        }
    }

    static class subTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            for (int i = 0; i<10; i++) {
                System.out.println("sub thread:" + i);
            }
            return "sub thread is finished";
        }
    }
    static class mainRunner implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i<10; i++) {
                System.out.println("main thread:" + i);
            }
        }
    }
}
