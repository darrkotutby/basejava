package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Test
    public void getAllTest() {
        assertArrayEquals(new Resume[]{RESUME_1, RESUME_3, RESUME_2}, STORAGE.getAll());
    }
}