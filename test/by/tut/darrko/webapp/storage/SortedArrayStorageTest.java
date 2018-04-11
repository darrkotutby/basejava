package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    private final Storage storage = new SortedArrayStorage();

    public SortedArrayStorageTest() {
        super();
        setStorage(storage);
    }

    @Test
    public void fillingTest() {
        Resume[] array = storage.getAll();
        for (int i = 1; i <= array.length - 1; i++) {
            Resume resume1 = array[i - 1];
            Resume resume2 = array[i];
            assertTrue(resume1.compareTo(resume2) < 0);
        }
    }

}