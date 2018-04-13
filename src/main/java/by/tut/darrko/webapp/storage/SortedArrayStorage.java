package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;

/**
 * Sorted Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    public SortedArrayStorage() {
    }

    @Override
    public void add(Resume resume, int index) {
        index = Math.abs(index + 1);
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    public void remove(String uuid, int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    protected int findResumeElementNumber(String uuid) {
        Resume searchedKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchedKey);
    }

}