package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> list = new ArrayList<>();
    @Override
    public void clearStorage() {
        list.clear();
    }

    @Override
    public void insertResume(Resume resume, Object searchKey) {
        list.add(resume);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return list.get((Integer) searchKey);
    }

    @Override
    public void removeResume(Object searchKey) {
        list.remove((int) searchKey);
    }

    @Override
    public Resume[] getAllResumes() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public int sizeResume() {
        return list.size();
    }

    @Override
    public void updateResume(Object searchKey, Resume resume) {
        list.set((Integer) searchKey, resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
