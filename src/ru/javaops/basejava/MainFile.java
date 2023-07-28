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

        allFiles(new File("C:\\Users\\Марина\\IdeaProjects\\basejava"), "");
    }

    public static void allFiles(File directory, String indent) {
        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("directory is empty");
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(indent + "D: " + file.getName());
                allFiles(file, indent + "\t");
            } else {
                System.out.println(indent + "F: " + file.getName());
            }
        }
    }
}
