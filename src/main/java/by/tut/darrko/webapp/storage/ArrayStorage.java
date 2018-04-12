package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public ArrayStorage() {
    }

    public void add(Resume resume, int index) {
        storage[size] = resume;
    }

    public void remove(String uuid, int index) {
        storage[index] = storage[size];
    }

    protected int findResumeElementNumber(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}