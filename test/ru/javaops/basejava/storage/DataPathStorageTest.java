package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serialization.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest{
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}
