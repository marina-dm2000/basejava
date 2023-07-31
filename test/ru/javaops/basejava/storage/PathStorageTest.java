package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serialization.ObjectStreamStorage;

public class PathStorageTest extends AbstractStorageTest{
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStorage()));
    }
}