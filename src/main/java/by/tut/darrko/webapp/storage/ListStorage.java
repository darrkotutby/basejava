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

    public Resume get(Object index) {
        return storage.get((Integer) index);
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    public void save(Resume resume, Object index) {
        storage.add(resume);
    }

    public void delete(Object index) {
        storage.remove(((Integer) index).intValue());
    }

    public void update(Resume resume, Object index) {
        storage.set((Integer) index, resume);
    }

    @Override
    boolean check(Object index) {
        return (Integer) index > -1;
    }

    protected Integer findResumeElementNumber(Resume resume) {
        return storage.indexOf(resume);
    }
}