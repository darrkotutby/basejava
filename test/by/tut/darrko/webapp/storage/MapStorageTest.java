package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
        array = new Resume[]{resume1, resume2, resume3};
    }
}