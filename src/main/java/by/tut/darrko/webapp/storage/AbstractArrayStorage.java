package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final int MAX_SIZE = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[MAX_SIZE];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int resumeIndex = findResumeElementNumber(uuid);
        if (resumeIndex > -1) {
            return storage[resumeIndex];
        }
        System.out.println("Resume " + uuid + " doesn't exists");
        return null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void update(Resume resume) {
        int resumeIndex = findResumeElementNumber(resume.getUuid());
        if (resumeIndex < 0) {
            System.out.println("Resume " + resume.getUuid() + " doesn't exists");
            return;
        }
        storage[resumeIndex] = resume;
    }

    protected abstract int findResumeElementNumber(String uuid);
}
