package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    private static final int MAX_SIZE = 10000;
    final Resume[] STORAGE;
    int size = 0;

    AbstractArrayStorage() {
        STORAGE = new Resume[MAX_SIZE];
    }

    protected abstract void add(Resume resume, int index);

    protected abstract void remove(String uuid, int index);

    public int getMaxSize() {
        return MAX_SIZE;
    }

    public void clear() {
        Arrays.fill(STORAGE, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        return STORAGE[isExists(uuid)];
    }

    /**
     * @return array, contains only Resumes in STORAGE (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }

    public void save(Resume resume) {
        if (size >= MAX_SIZE) {
            throw new NotExistStorageException("Can't save resume with uuid=" + resume.getUuid() +
                    ". Storage is full", resume.getUuid());
        }
        add(resume, isNotExists(resume.getUuid()));
        size++;
    }

    public void delete(String uuid) {
        remove(uuid, isExists(uuid));
        size--;
        STORAGE[size] = null;
    }

    public void update(Resume resume) {
        STORAGE[isExists(resume.getUuid())] = resume;
    }
}
