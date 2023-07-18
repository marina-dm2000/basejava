package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;
    
    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw  new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        
        this.directory = directory;
    }
    
    @Override
    protected void clearStorage() {
        
    }

    @Override
    protected void insertResume(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume getResume(File file) {
        return null;
    }

    @Override
    protected void removeResume(File file) {

    }

    @Override
    protected List<Resume> getAllResumes() {
        return null;
    }

    @Override
    protected int sizeStorage() {
        return 0;
    }

    @Override
    protected void updateResume(File file, Resume resume) {

    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;
}
