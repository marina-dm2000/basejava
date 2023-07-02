package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();
    @Override
    protected void clearStorage() {
        map.clear();
    }

    @Override
    protected void insertResume(Resume resume, Object searchKey) {
        map.put((String) searchKey, resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return map.get((String) searchKey);
    }

    @Override
    protected void removeResume(Object searchKey) {
        map.remove((String) searchKey);
    }

    @Override
    protected List<Resume> getAllResumes() {
        return (List<Resume>) map.values();
    }

    @Override
    protected int sizeStorage() {
        return map.size();
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        map.put((String) searchKey, resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey((String) searchKey);
    }
}
