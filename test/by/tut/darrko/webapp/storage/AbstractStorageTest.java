package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    Resume resume1 = new Resume("UUID1");
    Resume resume2 = new Resume("UUID2");
    Resume resume3 = new Resume("UUID3");
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
        assertEquals(resume1, storage.get(resume1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistsTest() {
        storage.get("test1");
    }

    @Test
    public void save() {
        Resume newResume = new Resume("test1");
        storage.save(newResume);
        assertEquals(4, storage.size());
        assertEquals(newResume, storage.get(newResume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(resume2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTest() {
        try {
            storage.delete(resume1.getUuid());
        } catch (NotExistStorageException e) {
            fail(e.getMessage());
        }
        assertEquals(2, storage.size());
        storage.get(resume1.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete("test1");
    }

    @Test
    public void getAllTest() {
        assertArrayEquals(array, storage.getAll());
    }

    @Test
    public void updateTest() {
        Resume newResume = new Resume(resume1.getUuid());
        storage.update(newResume);
        assertSame(newResume, storage.get(resume1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(new Resume("test1"));
    }
}