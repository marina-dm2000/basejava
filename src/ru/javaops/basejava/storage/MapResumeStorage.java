package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected void doClear() {
        map.clear();
    }

    @Override
    protected void doSave(Resume resume, Resume searchKey) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doDelete(Resume searchKey) {
        map.remove(searchKey.getUuid());
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected int sizeStorage() {
        return map.size();
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return map.containsValue(searchKey);
    }
}

