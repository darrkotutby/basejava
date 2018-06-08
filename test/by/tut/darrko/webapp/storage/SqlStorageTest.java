package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.Config;
import by.tut.darrko.webapp.model.ContactType;
import by.tut.darrko.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }

    @Test(expected = IllegalStateException.class)
    public void updateChanged() {
        Resume resume = new Resume("UUID_AWP", "Test");
        try {
            storage.save(resume);
            resume = storage.get("UUID_AWP");
            resume.addContact(ContactType.MAIL, "pilipenko@gmail.com");
            storage.update(resume);
        } catch (IllegalStateException e) {
            Assert.fail();
        }
        resume.addContact(ContactType.MAIL, "pilipenko1@gmail.com");
        storage.update(resume);
    }
}
