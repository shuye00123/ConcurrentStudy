package com.concurrent.study.java.Lock;

/**
 * Created by shuye on 2017/8/3.
 *
 * 对象锁
 * synchronized doSomething(){
 *     ...
 * }
 * synchronized(this or object){
 *     ...
 * }
 * 类锁
 * static synchronized doSomething(){
 *     ...
 * }
 * synchronized(xxx.class){
 *     ...
 * }
 */
public class SynchronizedTest {

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[50];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new runnable());
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }
}

class runnable implements Runnable{
    @Override
    public synchronized void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().toString() + "get this " + this.toString() + " lock " + i);
        }
        System.out.println(Thread.currentThread().toString() + " release this lock");
    }
}
