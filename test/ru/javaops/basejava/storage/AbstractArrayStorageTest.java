package ru.javaops.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    private final AbstractArrayStorage storage;

    public AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
        this.storage = storage;
    }

    @Test
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + (i + 1)));
            }
        } catch (Exception e) {
            Assertions.fail("Overflow occurred ahead of time");
        }
        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume("uuid")));
    }
}