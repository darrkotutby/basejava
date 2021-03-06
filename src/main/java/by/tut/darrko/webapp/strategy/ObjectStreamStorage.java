package by.tut.darrko.webapp.strategy;

import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage implements SerializationMethod {

    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
