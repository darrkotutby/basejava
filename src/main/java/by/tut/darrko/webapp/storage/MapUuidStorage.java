package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.TreeMap;

public class MapUuidStorage extends AbstractMapStorage {

    public MapUuidStorage() {
        storage = new TreeMap<>();
    }

    public Resume getByIndex(Object index) {
        return storage.get(index.toString());
    }

    public void saveByIndex(Resume resume, Object index) {
        storage.put(resume.getUuid(), resume);
    }

    public void deleteByIndex(Object index) {
        storage.remove(index.toString());
    }

    public void updateByIndex(Resume resume, Object index) {
        storage.put(index.toString(), resume);
    }

    protected Object findResumeElementNumber(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }

    @Override
    boolean check(Object index) {
        return index != null;
    }
}