package com.concurrent.study.java.SCMSW;

import java.util.concurrent.TimeUnit;

/**
 * Created by shuye on 2016/12/19.
 * week1 1
 *
 * 突然发现CSDN博客被封了我去他吗找时间自己弄个博客
 *
 * 七周七并发模型第一天
 * 本节介绍了如何创建线程，并用java对象的内置锁实现互斥。还介绍了线程与锁模型带来的三个主要危害--竞态条件、死锁和内存可见性，并讨论了一些帮助我们避免危害的准则：
 *   对共享变量的所有访问都需要同步化；
 *   读线程和写线程都需要同步化；
 *   按照约定的全局顺序来获取多把锁；
 *   持有锁的时间应尽可能短
 *
 * java线程之间通信是通过共享内存，静态域实例域和数组元素存储在堆内存中，堆内存线程间共享
 * 一致性内存模型 > JMM > CPU内存模型
 *
 * 线程安全的单例模式？  不一定，volatile？
 *
 * 反模式, 是指用来解决问题的带有共同性的不良方法。它们已经经过研究并分类，以防止日后重蹈覆辙，并能在研发尚未投产时辨认出来。
 */
public class disorder {
    static class Work1 implements Runnable{
        @Override
        public void run() {
            i = 1;
            j = 2;
            System.out.println(j);
            System.out.println(i);
        }
    }
    static class Work2 implements Runnable{
        @Override
        public void run() {
            i = 2;
            j = 1;
            System.out.println(j);
            System.out.println(i);
        }
    }
    static int i = 0;
    static int j = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Work1());
        Thread thread2 = new Thread(new Work1());
        thread1.start();
        thread2.start();
    }
}
