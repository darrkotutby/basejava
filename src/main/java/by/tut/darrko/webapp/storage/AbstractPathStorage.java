package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;


public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public long size() {
        try {
            return Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {

        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(path, CREATE))) {
            doWrite(r, out);
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume r, Path Path) {
        doUpdate(r, Path);
    }

    @Override
    protected Resume doGet(Path path) {
        try (InputStream in = new BufferedInputStream(
                Files.newInputStream(path, READ))) {
            return doRead(in);
        } catch (IOException e) {
            throw new StorageException("Path write error", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) throws StorageException {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
      /*  Stream<Path> stream;
        try {
            stream = Files.;

            {

            }


            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }


        if (Paths == null) {

        }
        List<Resume> list = new ArrayList<>(Paths.length);
        for (Path Path : Paths) {
            list.add(doGet(Path));
        }
        return list;*/
      return null;
    }
}
