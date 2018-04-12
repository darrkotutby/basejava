package by.tut.darrko.webapp;

import by.tut.darrko.webapp.exception.ExistStorageException;
import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.storage.AbstractArrayStorage;
import by.tut.darrko.webapp.storage.SortedArrayStorage;
import by.tut.darrko.webapp.storage.Storage;

/**
 * Test for ArrayStorage
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        ARRAY_STORAGE.save(r1);

        try {
            ARRAY_STORAGE.save(r1);
        } catch (ExistStorageException e) {
            System.out.println(e.getMessage());
        }

        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);

        printAll();

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        try {
            System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        } catch (NotExistStorageException e) {
            System.out.println(e.getMessage());
        }

        Resume r4 = new Resume("uuid4");

        try {
            ARRAY_STORAGE.update(r4);
        } catch (NotExistStorageException e) {
            System.out.println(e.getMessage());
        }

        r4 = new Resume("uuid3");
        ARRAY_STORAGE.update(r4);
        r3 = ARRAY_STORAGE.get(r4.getUuid());
        if (r3 == r4) {
            System.out.println("Resume updated");
        }
        try {
            ARRAY_STORAGE.delete("dummy");
        } catch (NotExistStorageException e) {
            System.out.println(e.getMessage());
        }
        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());

        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

        try {
            for (int i = 0; i <= ((AbstractArrayStorage) ARRAY_STORAGE).getMaxSize(); i++) {
                ARRAY_STORAGE.save(new Resume(Integer.valueOf(i).toString()));
            }
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Size: " + ARRAY_STORAGE.size());

        ARRAY_STORAGE.delete("5");

        printAll();

        ARRAY_STORAGE.save(new Resume("5"));

        printAll();

        ARRAY_STORAGE.clear();
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
