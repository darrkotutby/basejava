package by.tut.darrko.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String message, String uuid) {
        super(message, uuid);
    }
}
