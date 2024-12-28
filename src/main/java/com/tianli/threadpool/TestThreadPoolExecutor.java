package com.tianli.threadpool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadPoolExecutor {

    public static class MyTask implements Runnable{
        private final String name;
        private final long duration;

        public MyTask(String name) {
            this(name,0);
        }

        public MyTask(String name, long duration) {
            this.name = name;
            this.duration = duration;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String toString() {
            return "MyTask{" +
                    "name='" + name + '\'' +
                    ", duration=" + duration +
                    '}';
        }
    }

    public static void showState(ArrayBlockingQueue<Runnable> queue, ThreadPoolExecutor threadPoolExecutor){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Object> tasks = new ArrayList<>();
        for(Runnable runnable: queue){
            try{
                Field callable = FutureTask.class.getDeclaredField("callable");
                callable.setAccessible(true);
                Object adapter = callable.get(runnable);
                Class<?> clazz = Class.forName("java.util.concurrent.Executors$RunnableAdapter");
                Field task = clazz.getDeclaredField("task");
                task.setAccessible(true);
                Object o = task.get(adapter);
                tasks.add(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("pool size: " + threadPoolExecutor.getPoolSize());
        System.out.println("queue: " + tasks);
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);
        //LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 3, 0,
                TimeUnit.MILLISECONDS,
                queue,
                r -> new Thread(r,"myThread" + atomicInteger.getAndIncrement()),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        threadPoolExecutor.submit(new MyTask("1",360000));
        threadPoolExecutor.submit(new MyTask("2",360000));
        threadPoolExecutor.submit(new MyTask("3",360000));
        threadPoolExecutor.submit(new MyTask("4",360000));
        threadPoolExecutor.submit(new MyTask("5",360000));
        threadPoolExecutor.submit(new MyTask("6",360000));
        showState(queue,threadPoolExecutor);
    }
}
