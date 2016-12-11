package com.concurrent.study.java;

import java.util.concurrent.TimeUnit;

/**
 * Created by shuye on 2016/12/11.
 */
public class StringBufferAndBuilder {
    private static StringBuffer str1 = new StringBuffer(" hello ");
    private static StringBuilder str2 = new StringBuilder(" hello ");
    static volatile int i = 0;
    static volatile int j = 0;
    static class StringBufferWorker implements Runnable{
        private StringBuffer stringBuffer;

        public StringBufferWorker(StringBuffer stringBuffer) {
            this.stringBuffer = stringBuffer;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(100L);
                stringBuffer.append(i++);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class StringBuilderWorker implements Runnable {
        private StringBuilder stringBuilder;

        public StringBuilderWorker(StringBuilder stringBuilder) {
            this.stringBuilder = stringBuilder;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(100L);
                stringBuilder.append(j++);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i<10;i++) {
            Thread t1 = new Thread(new StringBufferWorker(str1));
            t1.start();
            Thread t2 = new Thread(new StringBuilderWorker(str2));
            t2.start();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("StringBuffer value"+str1.toString());
        System.out.println("StringBuilder value"+str2.toString());
    }
}
