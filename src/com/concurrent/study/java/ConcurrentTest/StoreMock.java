package com.concurrent.study.java.ConcurrentTest;

import java.util.concurrent.*;

/**
 * Created by shuye on 2017/1/2.
 */
public class StoreMock {
    private static ExecutorService service = Executors.newFixedThreadPool(8);
    private static final StoreMock s = new StoreMock();
    private int[] array;
    private StoreMock() {
        array = new int[4];
    }
    public static StoreMock getInstance(){
        return s;
    }
    //mock database select
    public int get(int i) throws ExecutionException, InterruptedException {
        Future future= service.submit(new getCall(i));
        return (int)future.get();
    }
    //mock database update
    public int set(int i, int value) throws ExecutionException, InterruptedException {
        Future future= service.submit(new setCall(i, value));
        return (int) future.get();
    }
    class getCall implements Callable<Integer> {
        private int i;
        public getCall(int i){
            this.i = i;
        }
        @Override
        public Integer call() throws Exception {
            if (i>4) return 0;
            return array[i];
        }
    }
    class setCall implements Callable<Integer> {
        private int i;
        private int v;
        public setCall(int i,int v){
            this.i = i;
            this.v = v;
        }
        @Override
        public Integer call() throws Exception {
            if (i>4) return 0;
            if (array[i]==1){
                return array[i];
            }
            //sleep mock thread wait
            TimeUnit.MILLISECONDS.sleep(100);
            array[i] += v;
            return array[i];
        }
    }
}