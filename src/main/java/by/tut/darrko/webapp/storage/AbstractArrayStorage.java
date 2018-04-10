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
        if (resumeIndex < 0) {
            System.out.println("Resume " + uuid + " doesn't exists");
            return null;
        }
        return storage[resumeIndex];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

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
        concreteSave(resume, resumeIndex);
    }

    public void delete(String uuid) {
        int resumeIndex = findResumeElementNumber(uuid);
        if (resumeIndex < 0) {
            System.out.println("Resume " + uuid + " doesn't exists");
            return;
        }
        concreteDelete(uuid, resumeIndex);
    }

    public void update(Resume resume) {
        int resumeIndex = findResumeElementNumber(resume.getUuid());
        if (resumeIndex < 0) {
            System.out.println("Resume " + resume.getUuid() + " doesn't exists");
            return;
        }
        storage[resumeIndex] = resume;
    }

    protected abstract void concreteSave(Resume resume, int index);

    protected abstract void concreteDelete(String uuid, int index);

    protected abstract int findResumeElementNumber(String uuid);
}
