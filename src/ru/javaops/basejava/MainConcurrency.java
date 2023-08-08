package ru.javaops.basejava;

import ru.javaops.basejava.util.DeadLock;

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

        String obj1 = "object1";
        String obj2 = "object2";
        DeadLock deadLock1 = new DeadLock(obj1, obj2);
        DeadLock deadLock2 = new DeadLock(obj2, obj1);
        deadLock1.threadStart();
        deadLock2.threadStart();
    }

    private synchronized void inc() {
        counter++;
    }
}
