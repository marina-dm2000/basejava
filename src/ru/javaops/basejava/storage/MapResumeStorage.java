package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();
    @Override
    protected void clearStorage() {
        map.clear();
    }

    @Override
    protected void insertResume(Resume resume, Object searchKey) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void removeResume(Object searchKey) {
        map.remove(((Resume) searchKey).getUuid());
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
    protected void updateResume(Object searchKey, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsValue((Resume) searchKey);
    }
}

