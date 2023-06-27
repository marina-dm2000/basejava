package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    protected void clearStorage() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void insertResume(Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertArrayResume(resume);
            size++;
        }
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    protected void removeResume(int index) {
        removeArrayResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume[] getAllResumes() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected int sizeResume() {
        return size;
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage[index] = resume;
    }

    protected abstract void removeArrayResume(int index);
    protected abstract void insertArrayResume(Resume resume);
}
