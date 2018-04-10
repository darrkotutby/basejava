package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void concreteSave(Resume resume, int index) {
        if (size == 0) {
            storage[size] = resume;
            size++;
            return;
        }

        if (storage[size - 1].compareTo(resume) < 0) {
            storage[size] = resume;
            size++;
            return;
        }

        index = Math.abs(index + 1);

        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
        size++;
    }

    @Override
    public void concreteDelete(String uuid, int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
        storage[size] = null;
    }

    protected int findResumeElementNumber(String uuid) {
        Resume searchedKey = new Resume();
        searchedKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchedKey);
    }

}