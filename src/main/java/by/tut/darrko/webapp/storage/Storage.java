package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

public interface Storage {
    void clear();

    int size();

    Resume get(Resume resume);

    Resume[] getAll();

    void save(Resume resume);

    void delete(Resume resume);

    void update(Resume resume);
}
