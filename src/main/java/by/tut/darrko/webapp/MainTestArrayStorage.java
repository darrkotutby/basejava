package by.tut.darrko.webapp;

import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.storage.ArrayStorage;
import by.tut.darrko.webapp.storage.SortedArrayStorage;
import by.tut.darrko.webapp.storage.Storage;

/**
 * Test for ArrayStorage
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);

        printAll();

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        Resume r4 = new Resume();
        r4.setUuid("uuid4");
        ARRAY_STORAGE.update(r4);
        r4.setUuid("uuid3");
        ARRAY_STORAGE.update(r4);
        r3 = ARRAY_STORAGE.get(r4.getUuid());
        if (r3 == r4) {
            System.out.println("Resume updated");
        }

        ARRAY_STORAGE.delete("dummy");

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());

        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
