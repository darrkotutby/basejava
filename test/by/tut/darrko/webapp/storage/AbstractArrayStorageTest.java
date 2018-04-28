package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowStorageTest() {
        try {
            for (int i = 0; i <= ((AbstractArrayStorage) storage).getMaxSize() - 4; i++) {
                storage.save(new Resume("test1"));
            }
        } catch (StorageException e) {
            fail("Storage is not full");
        }
        storage.save(new Resume("test1"));
    }
}
