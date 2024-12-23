package com.tianli.thread_creation;

/*
creation of threads using extending of Thread class
*/
public class MyThread extends Thread{

    @Override
    public void run() {
        System.out.println("we are currently in thread: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();

        t1.start();
        t2.start();
    }
}
