package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.HashMap;

public class MapResumeStorage extends AbstractMapStorage<Resume> {

    public MapResumeStorage() {
        storage = new HashMap<>();
    }

    @Override
    public Resume getByIndex(Resume index) {
        return storage.get(index.getUuid());
    }

    @Override
    public void saveByIndex(Resume resume, Resume index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void deleteByIndex(Resume index) {
        storage.remove(index.getUuid());
    }

    @Override
    public void updateByIndex(Resume resume, Resume index) {
        storage.put(index.getUuid(), resume);
    }

    @Override
    protected Resume findResumeElementNumber(String uuid) {
        return storage.get(uuid);
    }

    @Override
    boolean check(Resume index) {
        return index != null;
    }
}