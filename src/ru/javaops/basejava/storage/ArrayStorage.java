package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertArrayResume(Resume resume, Object searchKey) {
        storage[size] = resume;
    }

    @Override
    protected void removeArrayResume(int index) {
        storage[index] = storage[size - 1];
    }
}