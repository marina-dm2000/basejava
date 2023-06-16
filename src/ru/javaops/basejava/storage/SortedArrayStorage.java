package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void addElement(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            index = -index - 1;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void deleteElement(String uuid) {
        int index = getIndex(uuid);
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }
}
