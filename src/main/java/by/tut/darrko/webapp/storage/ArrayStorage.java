package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void concreteSave(Resume resume, int index) {
        storage[size] = resume;
        size++;
    }

    public void concreteDelete(String uuid, int index) {
        size--;
        storage[index] = storage[size];
        storage[size] = null;
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