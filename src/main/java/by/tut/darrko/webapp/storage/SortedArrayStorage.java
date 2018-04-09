package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (size >= MAX_SIZE) {
            System.out.println("Storage is full");
            return;
        }

        int resumeIndex = findResumeElementNumber(resume.getUuid());
        if (resumeIndex > -1) {
            System.out.println("Resume " + resume.getUuid() + " already exists");
            return;
        }

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

        resumeIndex = Math.abs(resumeIndex + 1);

        System.arraycopy(storage, resumeIndex, storage, resumeIndex + 1, size - resumeIndex);
        storage[resumeIndex] = resume;
        size++;
    }

    @Override
    public void delete(String uuid) {
        int resumeIndex = findResumeElementNumber(uuid);
        if (resumeIndex > -1) {
            System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, size - resumeIndex);
            size--;
            storage[size] = null;
        } else {
            System.out.println("Resume " + uuid + " doesn't exists");
        }
    }

    protected int findResumeElementNumber(String uuid) {
        Resume searchedKey = new Resume();
        searchedKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchedKey);
    }

}