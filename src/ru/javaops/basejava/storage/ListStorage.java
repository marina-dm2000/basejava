package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final ArrayList<Resume> list = new ArrayList<>();

    @Override
    public void doClear() {
        list.clear();
    }

    @Override
    public void doSave(Resume resume, Integer searchKey) {
        list.add(resume);
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    public void doDelete(Integer searchKey) {
        list.remove((int) searchKey);
    }

    @Override
    public List<Resume> doGetAll() {
        return list;
    }

    @Override
    public int sizeStorage() {
        return list.size();
    }

    @Override
    public void doUpdate(Integer searchKey, Resume resume) {
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
