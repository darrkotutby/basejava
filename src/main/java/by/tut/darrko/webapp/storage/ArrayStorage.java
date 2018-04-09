package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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
        storage[size] = resume;
        size++;
    }

    public void delete(String uuid) {
        int resumeIndex = findResumeElementNumber(uuid);
        if (resumeIndex > -1) {
            size--;
            storage[resumeIndex] = storage[size];
            storage[size] = null;
        } else {
            System.out.println("Resume " + uuid + " doesn't exists");
        }
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