package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract Object findResumeElementNumber(String uuid);

    public abstract Resume getUsingIndex(Object index);

    protected abstract void saveUsingIndex(Resume resume, Object index);

    protected abstract void deleteUsingIndex(Object index);

    protected abstract void updateUsingIndex(Resume resume, Object index);

    abstract boolean check(Object index);

    private Object isExists(Resume resume) {
        Object index = findResumeElementNumber(resume.getUuid());
        if (!check(index)) {
            throw new NotExistStorageException("Resume with uuid=" + resume.getUuid() + " doesn't exists");
        }
        return index;
    }

    private Object isNotExists(Resume resume) {
        Object index = findResumeElementNumber(resume.getUuid());
        if (check(index)) {
            throw new ExistStorageException("Resume with uuid=" + resume.getUuid() + " already exists");
        }
        return index;
    }

    @Override
    public Resume get(Resume resume) {
        return getUsingIndex(isExists(resume));
    }

    @Override
    public void save(Resume resume) {
        saveUsingIndex(resume, isNotExists(resume));
    }

    @Override
    public void delete(Resume resume) {
        deleteUsingIndex(isExists(resume));
    }

    @Override
    public void update(Resume resume) {
        updateUsingIndex(resume, isExists(resume));
    }
}
