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
    public void remove(Object index) {
        int idx = (Integer) index;
        System.arraycopy(STORAGE, idx + 1, STORAGE, idx, size - idx - 1);
    }

    protected Integer findResumeElementNumber(String uuid) {
        return Arrays.binarySearch(STORAGE, 0, size, new Resume(uuid));
    }
}