package ru.javaops.basejava;

import ru.javaops.basejava.util.DeadLock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();
    //private static final Object LOCK = new Object();
    //private static final Lock lock = new ReentrantLock();
    private static final ReentrantReadWriteLock REENTRANT_READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = REENTRANT_READ_WRITE_LOCK.writeLock();
    private static final Lock READ_LOCK = REENTRANT_READ_WRITE_LOCK.readLock();
    private static final ThreadLocal<SimpleDateFormat> SDT = ThreadLocal.withInitial(SimpleDateFormat::new);


    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                //throw new IllegalStateException();
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
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //CompletionService completionService = new ExecutorCompletionService(executorService);

        //List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() ->
                    //Thread thread = new Thread(() ->
            {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(SDT.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });

            //thread.start();
            //threads.add(thread);
        }


        /*threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });*/

        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        //System.out.println(counter);
        System.out.println(mainConcurrency.atomicCounter.get());

        String obj1 = "object1";
        String obj2 = "object2";
        DeadLock deadLock1 = new DeadLock(obj1, obj2);
        DeadLock deadLock2 = new DeadLock(obj2, obj1);
        deadLock1.threadStart();
        deadLock2.threadStart();
    }

    private void inc() {
        atomicCounter.incrementAndGet();

        /*lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }*/
    }
}
