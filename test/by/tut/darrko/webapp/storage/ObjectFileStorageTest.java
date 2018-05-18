package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.strategy.ObjectStreamStorage;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}
