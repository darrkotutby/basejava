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

    public Resume get(Object index) {
        return storage.get(index.toString());
    }

    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    public void save(Resume resume, Object index) {
        storage.put(resume.getUuid(), resume);
    }

    public void delete(Object index) {
        storage.remove(index.toString());
    }

    public void update(Resume resume, Object index) {
        save(resume, index);
    }

    protected Object findResumeElementNumber(Resume resume) {
        if (storage.containsKey(resume.getUuid())) {
            return resume.getUuid();
        }
        return null;
    }

    @Override
    boolean check(Object index) {
        return index != null;
    }
}