package com.jike.demo.concurrent;

/**
 * @author qu.kun
 * @date 2020/10/22
 * @description 实现Runnable接口的方式创建线程
 */
public class RunnableImpl implements Runnable {
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
