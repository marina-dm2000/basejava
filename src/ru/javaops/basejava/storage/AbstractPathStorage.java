package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        this.directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected void doClear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {

    }

    @Override
    protected Path getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected Resume doGet(Path Path) {
        return null;
    }

    @Override
    protected void doDelete(Path Path) {
    }

    @Override
    protected List<Resume> doGetAll() {
        return null;
    }

    @Override
    protected int sizeStorage() {
        return 0;
    }

    @Override
    protected void doUpdate(Path Path, Resume resume) {
    }

    @Override
    protected boolean isExist(Path Path) {
        return false;
    }

    private Path[] getPathList() {
        return null;
    }

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;
}
