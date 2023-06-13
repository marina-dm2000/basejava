package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {
    void clear();

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();

    int size();

    void update(Resume resume);
}