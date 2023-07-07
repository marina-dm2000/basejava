package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    protected void insertResume(Resume resume, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertArrayResume(resume, searchKey);
            size++;
        }
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void removeResume(Object searchKey) {
        removeArrayResume((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> getAllResumes() {
        return new ArrayList<>(List.of(Arrays.copyOfRange(storage, 0, size)));
    }

    @Override
    protected int sizeStorage() {
        return size;
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    protected abstract void removeArrayResume(int index);
    protected abstract void insertArrayResume(Resume resume, Object searchKey);
}
