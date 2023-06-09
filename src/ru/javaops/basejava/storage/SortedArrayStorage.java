package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> STORAGE_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "undefined");
        return Arrays.binarySearch(storage, 0, size, searchKey, STORAGE_COMPARATOR);
    }

    @Override
    protected void insertArrayResume(Resume resume, Integer searchKey) {
        int index = -(int) searchKey - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void removeArrayResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }
}
