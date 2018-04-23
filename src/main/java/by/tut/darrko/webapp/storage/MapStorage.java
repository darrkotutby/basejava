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

    public Resume get(String uuid) {
        isExists(uuid);
        return (Resume) storage.get(uuid);
    }

    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    public void save(Resume resume) {
        isNotExists(resume.getUuid());
        storage.put(resume.getUuid(), resume);
    }

    public void delete(String uuid) {
        isExists(uuid);
        storage.remove(uuid);
    }

    public void update(Resume resume) {
        isExists(resume.getUuid());
        storage.put(resume.getUuid(), resume);
    }

    protected int findResumeElementNumber(String uuid) {
        if (storage.containsValue(new Resume(uuid))) {
            return 1;
        }
        return -1;
    }
}