package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final ArrayList<Resume> list = new ArrayList<>();

    @Override
    public void clearStorage() {
        list.clear();
    }

    @Override
    public void insertResume(Resume resume, Integer searchKey) {
        list.add(resume);
    }

    @Override
    public Resume getResume(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    public void removeResume(Integer searchKey) {
        list.remove((int) searchKey);
    }

    @Override
    public List<Resume> getAllResumes() {
        return list;
    }

    @Override
    public int sizeStorage() {
        return list.size();
    }

    @Override
    public void updateResume(Integer searchKey, Resume resume) {
        list.set(searchKey, resume);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != -1;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
