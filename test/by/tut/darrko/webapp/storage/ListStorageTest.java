package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
        array = new Resume[]{resume1, resume3, resume2};
    }
}