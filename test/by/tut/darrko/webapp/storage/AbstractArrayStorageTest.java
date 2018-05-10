package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.storage.AbstractArrayStorage;
import by.tut.darrko.webapp.storage.AbstractStorageTest;
import by.tut.darrko.webapp.storage.Storage;
import org.junit.Assert;
import org.junit.Test;

/**
 * gkislin
 * 12.06.2016
 */
public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("Name" + i));
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume("Overflow"));
    }
}