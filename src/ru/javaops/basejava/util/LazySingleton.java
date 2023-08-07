package ru.javaops.basejava.util;

public class LazySingleton {
    volatile private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;

        /* Double-checked locking
            if (INSTANCE == null) {
                synchronized (LazySingleton.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new LazySingleton();
                    }
                }
            }
            return INSTANCE;
        */
    }
}
