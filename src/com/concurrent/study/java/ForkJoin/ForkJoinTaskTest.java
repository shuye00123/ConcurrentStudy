package com.concurrent.study.java.ForkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by shuye on 2016/12/25.
 */
public class ForkJoinTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        CountTask task = new CountTask(1,10);
        Future<Integer> result = pool.submit(task);
        System.out.println("sum result is "+result.get());
    }
    static class CountTask extends RecursiveTask<Integer> {
        private static int splitesize = 2;
        private int start, end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            boolean canCompute = (end - start)<=splitesize;
            if (canCompute){
                for(int i = start; i<=end; i++){
                    sum+=i;
                }
            }else {
                int middle = (start+end)/2;
                CountTask first = new CountTask(start, middle);
                CountTask second = new CountTask(middle+1, end);
                first.fork();
                second.fork();
                int firstResult = first.join();
                int secondResult = second.join();
                sum = firstResult+secondResult;
            }
            return sum;
        }
    }
}
