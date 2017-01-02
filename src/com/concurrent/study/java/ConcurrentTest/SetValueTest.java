package com.concurrent.study.java.ConcurrentTest;


import java.util.concurrent.ExecutionException;

/**
 * Created by shuye on 2017/1/2.
 */
public class SetValueTest {
    static class Runnable1 implements Runnable{
        private StoreMock storeMock;
        private int i;

        public Runnable1(StoreMock storeMock, int i) {
            this.storeMock = storeMock;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                System.out.println("set "+storeMock.set(i,0)+" value "+"to 0");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static class Runnable2 implements Runnable{
        private StoreMock storeMock;
        private int i;
        private int value;

        public Runnable2(StoreMock storeMock, int i, int value) {
            this.storeMock = storeMock;
            this.i = i;
            this.value = value;
        }
        @Override
        public void run() {
            try {
                if (storeMock.get(this.i)==1){
                    return;
                }
                System.out.println(i+" value now is "+storeMock.set(this.i, 1));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        StoreMock storeMock = StoreMock.getInstance();
        for (int i=0; i<4; i++){
            Thread thread = new Thread(new Runnable1(storeMock, i));
            thread.start();
            thread.join();
        }
        for (int i = 0; i<4; i++){
            new Thread(new Runnable2(storeMock, i, 1)).start();
            new Thread(new Runnable2(storeMock, i, 1)).start();
        }
    }
}
