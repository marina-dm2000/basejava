package ru.javaops.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File(".\\src\\ru\\javaops\\basejava");
        System.out.println(dir.isDirectory());
        for (String name : Objects.requireNonNull(dir.list())) {
            System.out.println(name);
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        allFiles(new File("C:\\Users\\ПГУПС\\IdeaProjects\\basejava"));
    }

    public static void allFiles(File directory) {
        for (File name : Objects.requireNonNull(directory.listFiles())) {
            if (name.isDirectory()) {
                allFiles(name);
            } else {
                System.out.println(name.getName());
            }
        }
    }
}
