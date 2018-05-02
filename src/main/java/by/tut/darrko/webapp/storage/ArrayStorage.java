package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

/**
 * Array based STORAGE for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void add(Resume resume, Integer index) {
        STORAGE[size] = resume;
    }

    public void remove(int index) {
        STORAGE[index] = STORAGE[size - 1];
    }

    protected Integer findResumeElementNumber(String uuid) {
        for (int i = 0; i < size; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}