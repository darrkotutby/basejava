package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    final Resume RESUME_1 = new Resume("UUID1");
    final Resume RESUME_2 = new Resume("UUID2");
    final Resume RESUME_3 = new Resume("UUID3");
    final Storage STORAGE;

    AbstractArrayStorageTest(Storage storage) {
        this.STORAGE = storage;
    }

    @Before
    public void setUp() {
        STORAGE.clear();
        STORAGE.save(RESUME_1);
        STORAGE.save(RESUME_3);
        STORAGE.save(RESUME_2);
    }

    @Test
    public void clearTest() {
        STORAGE.clear();
        assertEquals(0, STORAGE.size());
    }

    @Test
    public void sizeTest() {
        assertEquals(3, STORAGE.size());
    }

    @Test
    public void getTest() {
        assertEquals(new Resume(RESUME_1.getUuid()), STORAGE.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistsTest() {
        STORAGE.get("test1");
    }

    @Test
    public void save() {
        Resume newResume = new Resume("test1");
        STORAGE.save(newResume);
        assertEquals(newResume, STORAGE.get(newResume.getUuid()));
    }

    @Test(expected = StorageException.class)
    public void saveOverflowStorageTest() {
        try {
            for (int i = 0; i <= ((AbstractArrayStorage) STORAGE).getMaxSize() - 4; i++) {
                STORAGE.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Storage is not full");
        }
        STORAGE.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        STORAGE.save(new Resume(RESUME_2.getUuid()));
    }

    @Test
    public void deleteTest() {
        boolean flag = false;
        STORAGE.delete(RESUME_1.getUuid());
        assertEquals(2, STORAGE.size());
        try {
            STORAGE.get(RESUME_1.getUuid());
        } catch (NotExistStorageException e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        STORAGE.delete("test1");
    }

    @Test
    public void updateTest() {
        Resume newResume = new Resume(RESUME_1.getUuid());
        STORAGE.update(newResume);
        assertSame(newResume, STORAGE.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        STORAGE.update(new Resume("test1"));
    }
}