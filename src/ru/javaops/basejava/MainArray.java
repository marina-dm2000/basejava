package ru.javaops.basejava;

import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.storage.SortedArrayStorage;
import ru.javaops.basejava.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for ru.javaops.basejava.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private final static Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save fullName | update fullName | delete fullName | get fullName | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            StringBuilder fullName = new StringBuilder();
            if (params.length > 1) {
                for (int i = 1; i < params.length; i++) {
                    assert false;
                    fullName.append(params[i].intern());
                }

            }
            switch (params[0]) {
                case "list" -> printAll();
                case "size" -> System.out.println(ARRAY_STORAGE.size());
                case "save" -> {
                    r = new Resume(fullName.toString());
                    ARRAY_STORAGE.save(r);
                    printAll();
                }
                case "update" -> {
                    r = new Resume(fullName.toString());
                    ARRAY_STORAGE.update(r);
                    printAll();
                }
                case "delete" -> {
                    ARRAY_STORAGE.delete(fullName.toString());
                    printAll();
                }
                case "get" -> System.out.println(ARRAY_STORAGE.get(fullName.toString()));
                case "clear" -> {
                    ARRAY_STORAGE.clear();
                    printAll();
                }
                case "exit" -> {
                    return;
                }
                default -> System.out.println("Неверная команда.");
            }
        }
    }

    private static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
