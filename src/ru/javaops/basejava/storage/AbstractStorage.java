package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    public final void clear() {
        clearStorage();
    }

    public final void save(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertResume(resume);
        }
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index);
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        removeResume(index);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return getAllResumes();
    }

    public final int size() {
        return sizeResume();
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        updateResume(index, resume);
    }

    protected abstract void clearStorage();
    protected abstract void insertResume(Resume resume);
    protected abstract int getIndex(String uuid);
    protected abstract Resume getResume(int index);
    protected abstract void removeResume(int index);
    protected abstract Resume[] getAllResumes();
    protected abstract int sizeResume();
    protected abstract void updateResume(int index, Resume resume);
}
