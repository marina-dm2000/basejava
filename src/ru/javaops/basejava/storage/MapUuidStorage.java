package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected void clearStorage() {
        map.clear();
    }

    @Override
    protected void insertResume(Resume resume, String searchKey) {
        map.put(searchKey, resume);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume getResume(String searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected void removeResume(String searchKey) {
        map.remove(searchKey);
    }

    @Override
    protected List<Resume> getAllResumes() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected int sizeStorage() {
        return map.size();
    }

    @Override
    protected void updateResume(String searchKey, Resume resume) {
        map.put(searchKey, resume);
    }

    @Override
    protected boolean isExist(String searchKey) {
        return map.containsKey(searchKey);
    }
}
