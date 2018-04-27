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

    protected abstract void remove(Object index);

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

    public Resume getByIndex(Object index) {
        return STORAGE[(Integer) index];
    }

    /**
     * @return array, contains only Resumes in STORAGE (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }

    public void saveByIndex(Resume resume, Object index) {
        if (size >= MAX_SIZE) {
            throw new NotExistStorageException("Can't save resume. Storage is full");
        }
        add(resume, (Integer) index);
        size++;
    }

    public void deleteByIndex(Object index) {
        remove(index);
        size--;
        STORAGE[size] = null;
    }

    public void updateByIndex(Resume resume, Object index) {
        STORAGE[(Integer) index] = resume;
    }

    @Override
    boolean check(Object index) {
        return (Integer) index > -1;
    }
}
