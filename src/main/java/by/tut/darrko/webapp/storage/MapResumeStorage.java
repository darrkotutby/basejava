package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.TreeMap;

public class MapResumeStorage extends AbstractMapStorage {

    public MapResumeStorage() {
        storage = new TreeMap<>();
    }

    public Resume getByIndex(Object index) {
        return storage.get(((Resume) index).getUuid());
    }

    public void saveByIndex(Resume resume, Object index) {
        storage.put(resume.getUuid(), resume);
    }

    public void deleteByIndex(Object index) {
        storage.remove(((Resume) index).getUuid());
    }

    public void updateByIndex(Resume resume, Object index) {
        storage.put(((Resume) index).getUuid(), resume);
    }

    protected Object findResumeElementNumber(String uuid) {
        return storage.get(uuid);
    }

    @Override
    boolean check(Object index) {
        return index != null;
    }
}