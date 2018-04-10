package by.tut.darrko.webapp.by.tut.darrko.webapp.exception;

public class StorageException extends RuntimeException {
    private final String UUID;

    public StorageException(String message, String uuid) {
        super(message);
        UUID = uuid;
    }

    public String getUUID() {
        return UUID;
    }
}
