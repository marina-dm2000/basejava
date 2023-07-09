package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator
            .comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    public final void clear() {
        clearStorage();
    }

    public final void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getExistingSearchKey(resume.getUuid());
        insertResume(resume, searchKey);
    }

    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getNotExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getNotExistingSearchKey(uuid);
        removeResume(searchKey);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> list = getAllResumes();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    public final int size() {
        return sizeStorage();
    }

    public final void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        updateResume(searchKey, resume);
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " is already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " is not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void clearStorage();

    protected abstract void insertResume(Resume resume, SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract Resume getResume(SK searchKey);

    protected abstract void removeResume(SK searchKey);

    protected abstract List<Resume> getAllResumes();

    protected abstract int sizeStorage();

    protected abstract void updateResume(SK searchKey, Resume resume);

    protected abstract boolean isExist(SK searchKey);
}
