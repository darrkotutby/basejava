package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    Resume resume1 = new Resume("UUID1", "Alex Ivanov");
    Resume resume2 = new Resume("UUID2", "Petr Sidorov");
    Resume resume3 = new Resume("UUID3", "Herman Shults");
    Storage storage;
    Resume[] array = null;

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume3);
        storage.save(resume2);
    }

    @Test
    public void clearTest() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void sizeTest() {
        assertEquals(3, storage.size());
    }

    @Test
    public void getTest() {
        assertEquals(resume1, storage.get(resume1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistsTest() {
        storage.get(new Resume("test1"));
    }

    @Test
    public void save() {
        Resume newResume = new Resume("test1");
        storage.save(newResume);
        assertEquals(4, storage.size());
        assertEquals(newResume, storage.get(newResume));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(resume2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTest() {
        try {
            storage.delete(resume1);
        } catch (NotExistStorageException e) {
            fail(e.getMessage());
        }
        assertEquals(2, storage.size());
        storage.get(resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(new Resume("test1"));
    }

    @Test
    public void getAllTest() {
        assertArrayEquals(array, storage.getAll());
    }

    @Test
    public void updateTest() {
        Resume newResume = new Resume(resume1.getUuid());
        storage.update(newResume);
        assertSame(newResume, storage.get(resume1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(new Resume("test1"));
    }

    @Test
    public void getAllSortedTest() {
        List<Resume> list = new ArrayList<>();
        Resume[] array = new Resume[]{resume1, resume3, resume2};
        Collections.addAll(list, array);
        assertEquals(list, storage.getAllSorted());
    }
}