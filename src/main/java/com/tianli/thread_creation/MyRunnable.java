package com.tianli.thread_creation;

/*
creation of thread by creating a class implementing the Runnable interface
 */
public class MyRunnable implements Runnable{

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("we are currently inside the thread: " + Thread.currentThread().getName());
    }
}