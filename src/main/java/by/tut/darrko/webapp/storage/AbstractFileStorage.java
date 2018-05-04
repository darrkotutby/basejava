package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    protected abstract void add(Resume resume, File file);

    protected void remove(File file) {
        if (!file.delete()) {
            throw new StorageException("Can't delete " + file.getAbsolutePath());
        }
    }

    @Override
    protected File findResumeElementNumber(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public Resume getByIndex(File file) {
        return null;
    }

    @Override
    protected void saveByIndex(Resume resume, File file) {
        try {
            file.createNewFile();
            add(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", e);
        }
    }

    @Override
    protected void deleteByIndex(File file) {
        if (file.delete()) {
            throw new StorageException("Error can't delete " + file.getAbsolutePath());
        }
    }

    @Override
    protected void updateByIndex(Resume resume, File file) {
        remove(file);
        saveByIndex(resume, file);
    }

    @Override
    boolean check(File file) {
        return file.exists();
    }
}
