package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.strategy.ObjectStreamStorage;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}
