package com.jike.demo.concurrent;

/**
 * @author qu.kun
 * @date 2020/10/22
 * @description
 */
public class ThreadExtend extends Thread {

    @Override
    public void run() {
        try {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " run start");
            Thread.sleep(2000);
            System.out.println(threadName + " run over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
