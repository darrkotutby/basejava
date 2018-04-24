package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

/**
 * Array based STORAGE for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    public void add(Resume resume, int index) {
        STORAGE[size] = resume;
    }

    public void remove(Object index) {
        STORAGE[(Integer) index] = STORAGE[size - 1];
    }

    protected Integer findResumeElementNumber(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (STORAGE[i].equals(resume)) {
                return i;
            }
        }
        return -1;
    }
}