package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    private static final int MAX_SIZE = 10000;
    final Resume[] STORAGE;
    int size = 0;

    AbstractArrayStorage() {
        STORAGE = new Resume[MAX_SIZE];
    }

    protected abstract void add(Resume resume, Integer index);

    protected abstract void remove(int index);

    public int getMaxSize() {
        return MAX_SIZE;
    }

    @Override
    public void clear() {
        Arrays.fill(STORAGE, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume getByIndex(Integer index) {
        return STORAGE[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }

    @Override
    public void saveByIndex(Resume resume, Integer index) {
        if (size >= MAX_SIZE) {
            throw new NotExistStorageException("Can't save resume. Storage is full");
        }
        add(resume, index);
        size++;
    }

    @Override
    public void deleteByIndex(Integer index) {
        remove(index);
        size--;
        STORAGE[size] = null;
    }

    @Override
    public void updateByIndex(Resume resume, Integer index) {
        STORAGE[index] = resume;
    }

    @Override
    boolean check(Integer index) {
        return index > -1;
    }
}
