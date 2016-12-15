package com.concurrent.study.java;

import java.util.concurrent.TimeUnit;

/**
 * Created by shuye on 2016/12/13.
 */
public class NotifyAndWait {
    private static String[] strings = new String[1];

    public static void main(String[] args){
        strings[0] = "false";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MICROSECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (strings){
                    strings[0] = "true";
                    strings.notify();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (strings){
                    while (!strings[0].equals("true")){
                        try {
                            System.out.println("wait for notify");
                            strings.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("its runing!");
                }
            }
        }).start();
    }
}
