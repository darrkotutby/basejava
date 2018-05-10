package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.strategy.ObjectStreamStorage;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}
