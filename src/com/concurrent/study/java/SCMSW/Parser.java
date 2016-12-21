package com.concurrent.study.java.SCMSW;

import java.util.concurrent.BlockingQueue;

/**
 * Created by shuye on 2016/12/21.
 * Page类找不到就不写代码了
 *  fork/join框架是将任务放入ForkJoinPool线程池中的ForkJoin线程的内部队列执行，这与线程池类似，但是线程的执行单位是FoekJoinTask，
 * 用submit提交执行的任务会返回一个Future类。fork/join框架还有一个特性叫任务窃取(work-stealing)，能够让池中空闲的线程去尝试执行
 * 其他线程队列中的任务，从双向队列的尾部开始窃取。
 *  CountDownLatch和CyclicBarrier都包装了AQS类，都有计数的作用，CountDownLatch新建实例时传入了一个数，运行一个线程可以将这个数减
 *一，当数为零时后续线程会阻塞，用他可以控制运行的最大线程数。而CyclicBarrier是一个屏障，线程运行到屏障处会阻塞，当给定数目的线程
 *运行到屏障时，阻塞线程会被唤醒，第一个线程还可以执行特定的一个方法，这个类可以用在需要同步执行的地方。
 *  阿姆达尔定律是阿姆达尔提出的计算并行程序效率的方法，对于固定负载情况下描述并行处理效果的加速比S,有公式S=1/(1-a+a/n)，其中a是
 *并行计算部分所占比例，n是并行处理节点个数。
 *
 */
public class Parser {

}
