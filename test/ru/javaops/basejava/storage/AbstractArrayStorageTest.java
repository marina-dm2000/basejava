package ru.javaops.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
    }

    @Test
    public void save() {
    }

    @Test
    public void saveAlreadyExist() {
        Assertions.assertThrows(ExistStorageException.class , () -> {
            storage.save(new Resume(UUID_1));
        });
    }

    @Test
    public void saveOverflow() {
        /*Assertions.assertThrows(StorageException.class, ( -> {
            storage.save();
        }));*/
    }

    @Test
    public void get() {
        Assertions.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class , () -> {
            storage.get("dummy");
        });
    }

    @Test
    public void delete() {
    }

    @Test
    public void getAll() {
        Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assertions.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    public void update() {
    }
}