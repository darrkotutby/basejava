package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final String UUID_1 = "UUID1";
    private final String UUID_2 = "UUID2";
    private final String UUID_3 = "UUID3";
    private Storage storage;

    protected AbstractArrayStorageTest() {
        this(new ArrayStorage());
    }

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_2));
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
        Resume resume = new Resume(UUID_1);
        Resume resume1 = storage.get(UUID_1);
        assertEquals(resume, resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistsTest() {
        storage.get("test1");
    }

    @Test
    public void getAllTest() {
        Resume[] array = storage.getAll();
        assertEquals(3, array.length);
        Resume[] ethalonArray = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        for (Resume anEthalonResume : ethalonArray) {
            boolean flag = false;
            for (Resume resume : array) {
                if (anEthalonResume.equals(resume)) {
                    flag = true;
                }
            }
            if (!flag) {
                fail("Resume with uuid=" + anEthalonResume.getUuid() + " have to be returned by getAll");
            }
        }
    }

    @Test(expected = StorageException.class)
    public void saveTest() {
        try {
            for (int i = 0; i <= ((AbstractArrayStorage) storage).getMaxSize() - 4; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Storage is not full");
        }
        storage.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(new Resume(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTest() {
        try {
            storage.delete(UUID_1);
            assertEquals(2, storage.size());
        } catch (NotExistStorageException e) {
            fail("Resume have to be found");
        }
        Resume resume = storage.get(UUID_1);
        fail("Resume was deleted");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete("test1");
    }

    @Test
    public void updateTest() {
        Resume ethalonResume = storage.get(UUID_1);
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        Resume updatedResume = storage.get(UUID_1);
        assertNotSame(ethalonResume, updatedResume);
        assertSame(newResume, updatedResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(new Resume("test1"));
    }
}