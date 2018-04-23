package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

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
        return (Resume) storage.get(isExists(uuid));
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    public void save(Resume resume) {
        isNotExists(resume.getUuid());
        storage.add(resume);
    }

    public void delete(String uuid) {
        storage.remove(isExists(uuid));
    }

    public void update(Resume resume) {
        storage.set(isExists(resume.getUuid()), resume);
    }

    protected int findResumeElementNumber(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }
}