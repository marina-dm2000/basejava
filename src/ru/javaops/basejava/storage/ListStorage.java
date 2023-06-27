package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected final ArrayList<Resume> list = new ArrayList<>();
    @Override
    public void clearStorage() {
        list.clear();
    }

    @Override
    public void insertResume(Resume resume) {
        list.add(resume);
    }

    @Override
    public Resume getResume(int index) {
        return list.get(index);
    }

    @Override
    public void removeResume(int index) {
        list.remove(index);
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
    public void updateResume(int index, Resume resume) {
        list.set(index, resume);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume resume = new Resume(uuid);
        return list.indexOf(resume);
    }
}
