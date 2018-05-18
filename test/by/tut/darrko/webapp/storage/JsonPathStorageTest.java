package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.strategy.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}
