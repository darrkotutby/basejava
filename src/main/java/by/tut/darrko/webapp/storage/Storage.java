package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

public interface Storage {
    void clear();

    int size();

    Resume get(String uuid);

    Resume[] getAll();

    void save(Resume resume);

    void delete(String uuid);

    void update(Resume resume);
}
