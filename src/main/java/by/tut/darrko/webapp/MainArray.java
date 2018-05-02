package by.tut.darrko.webapp;

import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.exception.StorageException;
import by.tut.darrko.webapp.model.Resume;
import by.tut.darrko.webapp.storage.ArrayStorage;
import by.tut.darrko.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Test for ArrayStorage
 */
public class MainArray {

    private final static Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid | delete uuid | get uuid | " +
                    "clear | update uuid fullName |exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String param = null;
            if (params.length == 2) {
                param = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    r = new Resume(param);
                    try {
                        ARRAY_STORAGE.save(r);
                        printAll();
                    } catch (StorageException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "delete":
                    try {
                        ARRAY_STORAGE.delete(param);
                        printAll();
                    } catch (NotExistStorageException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "get":
                    try {
                        System.out.println(ARRAY_STORAGE.get(param));
                    } catch (NotExistStorageException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "update":
                    if (params.length > 1) {
                        try {
                            Resume resume = new Resume(param, params[2]);
                            ARRAY_STORAGE.update(resume);
                            printAll();
                        } catch (NotExistStorageException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Wrong format. You have to use update uuid fullName");
                    }
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void printAll() {
        Resume[] all = ARRAY_STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}