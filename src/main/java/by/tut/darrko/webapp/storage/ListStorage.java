package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    protected List<Resume> storage;

    public ListStorage() {
        storage = new ArrayList<>();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume getByIndex(Integer index) {
        return storage.get(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public void saveByIndex(Resume resume, Integer index) {
        storage.add(resume);
    }

    @Override
    public void deleteByIndex(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    public void updateByIndex(Resume resume, Integer index) {
        storage.set(index, resume);
    }

    @Override
    boolean check(Integer index) {
        return index > -1;
    }

    protected Integer findResumeElementNumber(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}