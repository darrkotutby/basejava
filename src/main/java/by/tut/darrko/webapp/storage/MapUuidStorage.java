package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.HashMap;

public class MapUuidStorage extends AbstractMapStorage {

    public MapUuidStorage() {
        storage = new HashMap<>();
    }

    public Resume getByIndex(Object index) {
        return storage.get(index.toString());
    }

    public void saveByIndex(Resume resume, Object index) {
        storage.put(index.toString(), resume);
    }

    public void deleteByIndex(Object index) {
        storage.remove(index.toString());
    }

    public void updateByIndex(Resume resume, Object index) {
        saveByIndex(resume, index);
    }

    protected Object findResumeElementNumber(String uuid) {
        return uuid;
    }

    @Override
    boolean check(Object index) {
        return storage.containsKey(index.toString());
    }
}