package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;

/**
 * Sorted Array based STORAGE for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void add(Resume resume, int index) {
        index = Math.abs(index + 1);
        System.arraycopy(STORAGE, index, STORAGE, index + 1, size - index);
        STORAGE[index] = resume;
    }

    @Override
    public void remove(String uuid, int index) {
        System.arraycopy(STORAGE, index + 1, STORAGE, index, size - index - 1);
    }

    protected int findResumeElementNumber(String uuid) {
        Resume searchedKey = new Resume(uuid);
        return Arrays.binarySearch(STORAGE, 0, size, searchedKey);
    }
}