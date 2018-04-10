package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractArrayStorageTest {
    private final String UUID_1 = "UUID1";
    private final String UUID_2 = "UUID2";
    private final String UUID_3 = "UUID3";
    private Storage storage;

    public AbstractArrayStorageTest() {

    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }
}