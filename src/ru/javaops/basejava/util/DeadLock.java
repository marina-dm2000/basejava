package ru.javaops.basejava.util;

public class DeadLock {
    private final Object object1;
    private final Object object2;

    public DeadLock(Object object1, Object object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    public void threadStart() {
        new Thread(() -> {
            synchronized (object1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (object2) {
                    System.out.println("Block in thread");
                }
            }
        }).start();
    }
}
