package by.tut.darrko.webapp.by.tut.darrko.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String message, String uuid) {
        super(message, uuid);
    }
}
