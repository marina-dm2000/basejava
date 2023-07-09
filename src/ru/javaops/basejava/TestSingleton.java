package ru.javaops.basejava;

import ru.javaops.basejava.model.SectionType;

public class TestSingleton {
    private static final TestSingleton ourInstance = new TestSingleton();

    private static TestSingleton getInstance() {
        return ourInstance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        System.out.println(TestSingleton.getInstance().toString());
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.name());
        System.out.println(instance.ordinal());
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }

    public enum Singleton {
        INSTANCE
    }
}
