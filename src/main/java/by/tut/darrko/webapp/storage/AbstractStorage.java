package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract Object findResumeElementNumber(String uuid);
    public abstract Resume get(Object index);
    protected abstract void save(Resume resume, Object index);
    protected abstract void delete(Object index);
    protected abstract void update(Resume resume, Object index);
    abstract boolean isExists(Object index);

    Object isExists(String uuid) {
        Object index = findResumeElementNumber(uuid);
        if (!isExists(index)) {
            throw new NotExistStorageException("Resume with uuid=" + uuid + " doesn't exists");
        }
        return index;
    }

    Object isNotExists(String uuid) {
        Object index = findResumeElementNumber(uuid);
        if (isExists(index)) {
            throw new ExistStorageException("Resume with uuid=" + uuid + " already exists");
        }
        return index;
    }

    @Override
    public Resume get(String uuid) {
        return get(isExists(uuid));
    }

    @Override
    public void save(Resume resume) {
        save(resume, isNotExists(resume.getUuid()));
    }

    @Override
    public void delete(String uuid) {
        delete(isExists(uuid));
    }

    @Override
    public void update(Resume resume) {
        update(resume, isExists(resume.getUuid()));
    }
}
