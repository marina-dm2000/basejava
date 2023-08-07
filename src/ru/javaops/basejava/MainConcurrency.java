package ru.javaops.basejava;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() +
                        ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
                    counter++;
                }
            }
        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });

            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(counter);

        createDeadlock();
    }

    private synchronized void inc() {
        counter++;
    }

    private static void createDeadlock() {
        MainConcurrency concurrency1 = new MainConcurrency();
        MainConcurrency concurrency2 = new MainConcurrency();

        Thread thread0 = new Thread(() -> {
            synchronized (concurrency1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (concurrency2) {
                    System.out.println("Block in thread-0");
                }
            }
        });

        Thread thread1 = new Thread(() -> {
            synchronized (concurrency2) {
                synchronized (concurrency1) {
                    System.out.println("Block in thread-1");
                }
            }
        });

        thread0.start();
        thread1.start();
    }
}
