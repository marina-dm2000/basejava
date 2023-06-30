package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    public final void clear() {
        clearStorage();
    }

    public final void save(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        insertResume(resume, searchKey);
    }

    public final Resume get(String uuid) {
        Object searchKey = getNotExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    public final void delete(String uuid) {
        Object searchKey = getNotExistingSearchKey(uuid);
        removeResume(searchKey);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return getAllResumes();
    }

    public final int size() {
        return sizeStorage();
    }

    public final void update(Resume resume) {
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        updateResume(searchKey, resume);
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void clearStorage();
    protected abstract void insertResume(Resume resume, Object searchKey);
    protected abstract Object getSearchKey(String uuid);
    protected abstract Resume getResume(Object searchKey);
    protected abstract void removeResume(Object searchKey);
    protected abstract Resume[] getAllResumes();
    protected abstract int sizeStorage();
    protected abstract void updateResume(Object searchKey, Resume resume);
    protected abstract boolean isExist(Object searchKey);
}
