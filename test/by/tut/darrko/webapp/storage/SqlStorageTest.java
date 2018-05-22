package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getSqlStorage());
    }
}
