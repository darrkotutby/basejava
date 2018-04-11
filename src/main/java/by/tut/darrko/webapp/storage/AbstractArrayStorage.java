package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected final int MAX_SIZE;
    protected int size = 0;
    protected Resume[] storage;

    public AbstractArrayStorage() {
        this(10000);
    }

    public AbstractArrayStorage(int maxSize) {
        if (maxSize < 1)
            throw new StorageException("Initial size of the storage must be more than 0. MAX_SIZE=" + maxSize, null);
        MAX_SIZE = maxSize;
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
        int resumeIndex = findResumeElementNumber(uuid);
        if (resumeIndex < 0) {
            throw new NotExistStorageException("Resume with uuid=" + uuid + " doesn't exists", uuid);
        }
        return storage[resumeIndex];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void save(Resume resume) {
        if (size >= MAX_SIZE) {
            throw new NotExistStorageException("Can't save resume with uuid=" + resume.getUuid() + ". Storage is full", resume.getUuid());
        }
        if (size == 0 || storage[size - 1].compareTo(resume) < 0) {
            storage[size] = resume;
        } else {
            int resumeIndex = findResumeElementNumber(resume.getUuid());
            if (resumeIndex > -1) {
                throw new ExistStorageException("Resume with uuid=" + resume.getUuid() + " already exists", resume.getUuid());
            }
            add(resume, resumeIndex);
        }
        size++;
    }

    public void delete(String uuid) {
        int resumeIndex = findResumeElementNumber(uuid);
        if (resumeIndex < 0) {
            throw new NotExistStorageException("Resume with uuid=" + uuid + " doesn't exists", uuid);
        }
        size--;
        remove(uuid, resumeIndex);
    }

    public void update(Resume resume) {
        int resumeIndex = findResumeElementNumber(resume.getUuid());
        if (resumeIndex < 0) {
            throw new NotExistStorageException("Resume with uuid=" + resume.getUuid() + " doesn't exists", resume.getUuid());
        }
        storage[resumeIndex] = resume;
    }


}
