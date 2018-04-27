package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract Object findResumeElementNumber(String uuid);

    public abstract Resume getByIndex(Object index);

    protected abstract void saveByIndex(Resume resume, Object index);

    protected abstract void deleteByIndex(Object index);

    protected abstract void updateByIndex(Resume resume, Object index);

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
        return getByIndex(isExists(resume));
    }

    @Override
    public void save(Resume resume) {
        saveByIndex(resume, isNotExists(resume));
    }

    @Override
    public void delete(Resume resume) {
        deleteByIndex(isExists(resume));
    }

    @Override
    public void update(Resume resume) {
        updateByIndex(resume, isExists(resume));
    }
}
