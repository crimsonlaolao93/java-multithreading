package com.tianli.thread_creation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutors implements Runnable{


    @Override
    public void run() {
        System.out.println("running on a fixed thread pool...");
    }

    public static void main(String[] args) {
        ExecutorService threadpool = Executors.newFixedThreadPool(3);
        threadpool.submit(new MyExecutors());
    }
}
