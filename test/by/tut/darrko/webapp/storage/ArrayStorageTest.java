package by.tut.darrko.webapp.storage;

import by.tut.darrko.webapp.model.Resume;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
        array = new Resume[]{resume1, resume3, resume2};
    }
}