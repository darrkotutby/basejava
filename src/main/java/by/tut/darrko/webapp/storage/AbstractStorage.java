package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract Object findResumeElementNumber(Resume resume);

    public abstract Resume get(Object index);

    protected abstract void save(Resume resume, Object index);

    protected abstract void delete(Object index);

    protected abstract void update(Resume resume, Object index);

    abstract boolean check(Object index);

    private Object isExists(Resume resume) {
        Object index = findResumeElementNumber(resume);
        if (!check(index)) {
            throw new NotExistStorageException("Resume with uuid=" + resume.getUuid() + " doesn't exists");
        }
        return index;
    }

    private Object isNotExists(Resume resume) {
        Object index = findResumeElementNumber(resume);
        if (check(index)) {
            throw new ExistStorageException("Resume with uuid=" + resume.getUuid() + " already exists");
        }
        return index;
    }

    @Override
    public Resume get(Resume resume) {
        return get(isExists(resume));
    }

    @Override
    public void save(Resume resume) {
        save(resume, isNotExists(resume));
    }

    @Override
    public void delete(Resume resume) {
        delete(isExists(resume));
    }

    @Override
    public void update(Resume resume) {
        update(resume, isExists(resume));
    }
}
