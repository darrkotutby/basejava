package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

public class MapUuidStorageTest extends AbstractStorageTest {

    public MapUuidStorageTest() {
        super(new MapUuidStorage());
        array = new Resume[]{resume1, resume2, resume3};
    }
}