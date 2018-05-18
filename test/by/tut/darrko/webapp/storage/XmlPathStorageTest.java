package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.strategy.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}
