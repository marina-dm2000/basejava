package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    protected void assertArrayEquals(Resume[] resumes) {

    }
}