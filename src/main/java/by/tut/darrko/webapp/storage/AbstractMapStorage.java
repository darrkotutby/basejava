package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.Map;

public abstract class AbstractMapStorage extends AbstractStorage {

    protected Map<String, Resume> storage;

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }
}