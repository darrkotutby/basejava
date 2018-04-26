package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage;

    public MapStorage() {
        storage = new TreeMap<>();
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    public Resume getUsingIndex(Object index) {
        return storage.get(index.toString());
    }

    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    public void saveUsingIndex(Resume resume, Object index) {
        storage.put(resume.getUuid(), resume);
    }

    public void deleteUsingIndex(Object index) {
        storage.remove(index.toString());
    }

    public void updateUsingIndex(Resume resume, Object index) {
        storage.put(index.toString(), resume);
    }

    protected Object findResumeElementNumber(String uuid) {
        for (Map.Entry entry : storage.entrySet()) {
            if (uuid.equals(((Resume) entry.getValue()).getUuid())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    boolean check(Object index) {
        return index != null;
    }
}