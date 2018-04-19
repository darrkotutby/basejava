package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
        array = new Resume[]{resume1, resume2, resume3};
    }
}