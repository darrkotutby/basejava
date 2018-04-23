package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public abstract class ListStorage implements Storage {

    protected List<Resume> storage;

    public ListStorage() {
        storage = new ArrayList<>();
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    public Resume get(String uuid) {
        int resumeIndex = storage.indexOf(new Resume(uuid));
        if (resumeIndex < 0) {
            throw new NotExistStorageException("Resume with uuid=" + uuid + " doesn't exists", uuid);
        }
        return (Resume) storage.get(resumeIndex);
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    public void save(Resume resume) {
        int resumeIndex = storage.indexOf(resume);
        if (resumeIndex > -1) {
            throw new ExistStorageException("Resume with uuid=" + resume.getUuid() + " already exists", resume.getUuid());
        }
        storage.add(resume);
    }

    public void delete(String uuid) {
        int resumeIndex = storage.indexOf(new Resume(uuid));
        if (resumeIndex < 0) {
            throw new NotExistStorageException("Resume with uuid=" + uuid + " doesn't exists", uuid);
        }
        storage.remove(resumeIndex);
    }

    public void update(Resume resume) {
        int resumeIndex = storage.indexOf(resume);
        if (resumeIndex < 0) {
            throw new NotExistStorageException("Resume with uuid=" + resume.getUuid() + " doesn't exists", resume.getUuid());
        }
        storage.set(resumeIndex, resume);
    }


}