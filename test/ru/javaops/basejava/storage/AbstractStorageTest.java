package ru.javaops.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.javaops.basejava.ResumeTestData;
import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.Resume;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("storage");
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        try {
            RESUME_1 = ResumeTestData.createResume(UUID_1, "name1");
            RESUME_2 = ResumeTestData.createResume(UUID_2, "name2");
            RESUME_3 = ResumeTestData.createResume(UUID_3, "name3");
            RESUME_4 = ResumeTestData.createResume(UUID_4, "name4");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new ArrayList<>());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    public void saveAlreadyExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumes = List.of(RESUME_1, RESUME_2, RESUME_3);
        assertArrayEquals(resumes);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, "newName");
        storage.update(resume);
        Assertions.assertEquals(resume, storage.get(UUID_1));
    }

    @Test
    public void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(UUID_NOT_EXIST)));
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertArrayEquals(List<Resume> resumes) {
        Assertions.assertEquals(resumes, storage.getAllSorted());
    }
}