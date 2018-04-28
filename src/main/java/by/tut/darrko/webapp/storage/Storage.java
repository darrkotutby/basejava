package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    int size();

    Resume get(String uuid);

    Resume[] getAll();

    List<Resume> getAllSorted();

    void save(Resume resume);

    void delete(String uuid);

    void update(Resume resume);
}
