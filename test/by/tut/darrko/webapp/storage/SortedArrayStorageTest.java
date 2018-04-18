package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    public void getAllTest() {
        assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3}, STORAGE.getAll());
    }
}