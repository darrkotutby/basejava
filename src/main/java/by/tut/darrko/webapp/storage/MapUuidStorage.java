package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.HashMap;

public class MapUuidStorage extends AbstractMapStorage<String> {

    public MapUuidStorage() {
        storage = new HashMap<>();
    }

    public Resume getByIndex(String index) {
        return storage.get(index);
    }

    public void saveByIndex(Resume resume, String index) {
        storage.put(index, resume);
    }

    public void deleteByIndex(String index) {
        storage.remove(index);
    }

    public void updateByIndex(Resume resume, String index) {
        saveByIndex(resume, index);
    }

    protected String findResumeElementNumber(String uuid) {
        return uuid;
    }

    @Override
    boolean check(String index) {
        return storage.containsKey(index);
    }
}