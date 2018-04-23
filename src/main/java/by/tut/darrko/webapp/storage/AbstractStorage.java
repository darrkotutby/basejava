package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;

public abstract class AbstractStorage implements Storage {
    protected abstract int findResumeElementNumber(String uuid);

    int isExists(String uuid) {
        int index = findResumeElementNumber(uuid);
        if (index < 0) {
            throw new NotExistStorageException("Resume with uuid=" + uuid + " doesn't exists", uuid);
        }
        return index;
    }

    int isNotExists(String uuid) {
        int index = findResumeElementNumber(uuid);
        if (index > -1) {
            throw new ExistStorageException("Resume with uuid=" + uuid + " already exists",
                    uuid);
        }
        return index;
    }
}
