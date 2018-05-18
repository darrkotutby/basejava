package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.strategy.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}
