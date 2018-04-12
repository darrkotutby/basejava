package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int MAX_SIZE = 10000;
    protected int size = 0;
    protected Resume[] storage;

    protected AbstractArrayStorage() {
        storage = new Resume[MAX_SIZE];
    }

    protected abstract void add(Resume resume, int index);

    protected abstract void remove(String uuid, int index);

    protected abstract int findResumeElementNumber(String uuid);

    public int getMaxSize() {
        return MAX_SIZE;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = findResumeElementNumber(uuid);
        if (index < 0) {
            throw new NotExistStorageException("Resume with uuid=" + uuid + " doesn't exists", uuid);
        }
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void save(Resume resume) {
        if (size >= MAX_SIZE) {
            throw new NotExistStorageException("Can't save resume with uuid=" + resume.getUuid() + ". Storage is full",
                    resume.getUuid());
        }
        int index = findResumeElementNumber(resume.getUuid());
        if (index > -1) {
            throw new ExistStorageException("Resume with uuid=" + resume.getUuid() + " already exists",
                    resume.getUuid());
        }
        add(resume, index);

        size++;
    }

    public void delete(String uuid) {
        int index = findResumeElementNumber(uuid);
        if (index < 0) {
            throw new NotExistStorageException("Resume with uuid=" + uuid + " doesn't exists", uuid);
        }
        size--;
        remove(uuid, index);
        storage[size] = null;
    }

    public void update(Resume resume) {
        int index = findResumeElementNumber(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException("Resume with uuid=" + resume.getUuid() + " doesn't exists",
                    resume.getUuid());
        }
        storage[index] = resume;
    }


}
