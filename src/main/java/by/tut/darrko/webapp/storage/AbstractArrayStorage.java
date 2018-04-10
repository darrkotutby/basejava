package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected final int MAX_SIZE = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[MAX_SIZE];

    protected abstract void saveToArray(Resume resume, int index);

    protected abstract void deleteFromArray(String uuid, int index);

    protected abstract int findResumeElementNumber(String uuid);

    public int getMaxSize(){
        return MAX_SIZE;
    }

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
            System.out.println("Resume with uuid=" + uuid + " doesn't exists");
            return null;
            // throw new NotExistStorageException("Resume " + uuid + " doesn't exists", uuid);
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
            System.out.println("Can't save resume with uuid=" + resume.getUuid() + ". Storage is full");
            return;
            // throw new NotExistStorageException("Storage is full", resume.getUuid());
        }
        int resumeIndex = findResumeElementNumber(resume.getUuid());
        if (resumeIndex > -1) {
            System.out.println("Resume with uuid=" + resume.getUuid() + " already exists");
            return;
            // throw new ExistStorageException("Resume " + resume.getUuid() + " already exists", resume.getUuid());
        }
        saveToArray(resume, resumeIndex);
        size++;
    }

    public void delete(String uuid) {
        int resumeIndex = findResumeElementNumber(uuid);
        if (resumeIndex < 0) {
            System.out.println("Resume with uuid=" + uuid + " doesn't exists");
            return;
            // throw new NotExistStorageException("Resume " + uuid + " doesn't exists", uuid);
        }
        deleteFromArray(uuid, resumeIndex);
    }

    public void update(Resume resume) {
        int resumeIndex = findResumeElementNumber(resume.getUuid());
        if (resumeIndex < 0) {
            System.out.println("Resume with uuid=" + resume.getUuid() + " doesn't exists");
            return;
            // throw new NotExistStorageException("Resume " + resume.getUuid() + " doesn't exists", resume.getUuid());
        }
        storage[resumeIndex] = resume;
    }


}
