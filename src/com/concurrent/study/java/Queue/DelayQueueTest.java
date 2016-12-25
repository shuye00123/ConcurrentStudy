package com.concurrent.study.java.Queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuye on 2016/12/16.
 */
public class DelayQueueTest {
    static class Info implements Delayed {
        private String value;
        private long delay;
        private long start;

        public Info(String value, long delay) {
            this.value = value;
            this.delay = delay;
            start = TimeUnit.NANOSECONDS.convert(delay, TimeUnit.MICROSECONDS) + System.nanoTime();
        }

        public String getValue() {
            return value+" delay "+delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(start - System.nanoTime(), unit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            Info info = (Info) o;
            return start>info.start?1:(start<info.start?-1:0);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Info> queue = new DelayQueue<>();
        for (int i = 0; i<10; i++) {
            Info info = new Info("worker"+i, Math.round((Math.random()*10 + 1)));
            queue.put(info);
        }
        for (int i = 0; i<10 ; i++) {
            System.out.println(queue.take().getValue());
        }
    }
}
